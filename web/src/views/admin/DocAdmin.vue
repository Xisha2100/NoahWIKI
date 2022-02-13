<template>

  <a-layout>

    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >

      <a-row :gutter="24">
        <a-col :span="8">
          <p>
            <a-form layout="inline" :model="param">
              <a-form-item>
                <a-button type="primary" @click="handleQuery()">
                  刷新
                </a-button>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" @click="add()">
                  新增
                </a-button>
              </a-form-item>
            </a-form>
          </p>
          <a-table
              v-if="level1.length > 0 "
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
              size="small"
              :defaultExpandAllRows="true"
          >
            <template #name="{text,record}">
              {{ record.sort }} {{ text }}
              <!--          <img v-if="cover" :src="cover" alt="avatar" />-->
            </template>

            <template v-slot:action="{text,record}">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)" size="small">
                  编辑
                </a-button>
                <a-popconfirm
                    title="删除后不可恢复，请再次确认"
                    ok-text="是"
                    cancel-text="否"
                    @confirm="deleteDoc(record.id)"
                >
                  <a-button type="danger" size="small">
                    删除
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>

        <a-col :span="16">
          <p>
            <a-form-item>
              <a-button type="primary" @click="handleSave()">
                保存
              </a-button>
            </a-form-item>
          </p>
          <a-form
              :model="doc"
              layout="vertical"
          >

            <a-form-item>
              <a-input v-model:value="doc.name" :placeholder="名称"/>
            </a-form-item>

            <a-form-item>
              <a-tree-select
                  v-model:value="doc.parent"
                  style="width: 100%"
                  :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                  placeholder="请选择父文档"
                  tree-default-expand-all
                  :tree-data="treeSelectData"
                  :fieldNames="{label: 'name',key: 'id',value: 'id' }"
              >
              </a-tree-select>
            </a-form-item>

            <a-form-item>
              <a-input v-model:value="doc.sort" placeholder="顺序"/>
            </a-form-item>

            <a-form-item>
              <a-button type="primary" @click="handlePreviewContent()">
                <EyeOutlined /> 内容预览
              </a-button>
            </a-form-item>

            <a-form-item label="内容">
              <div id='editor'></div>
            </a-form-item>

          </a-form>
        </a-col>
      </a-row>

      <a-drawer width="900" placement="right" :closable="false" :visible="drawerVisible" @close="onDrawerClose">
        <div class="wangeditor" :innerHTML="previewHtml"></div>
      </a-drawer>

    </a-layout-content>
  </a-layout>


</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import E from 'wangeditor'

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const route = useRoute();
    const docs = ref();
    const level1 = ref();
    level1.value=[];
    const param = ref();
    param.value = {};
    // const ebooks1 = reactive({books: []});
    const loading = ref(false);
    const columns = [
      {
        title: '名称',
        dataIndex: 'name',
        slots: {customRender: 'name'}
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    //数据查询
    const handleQuery = () => {
      loading.value = true;
      level1.value = [];
      axios.get("doc/all/"+route.query.musicId).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {

          docs.value = data.content;
          level1.value = [];
          level1.value = Tool.array2Tree(docs.value, 0);

          treeSelectData.value=Tool.copy(level1.value);
          treeSelectData.value.unshift({id:0,name:'无'});
          //重置分页组件
        } else {
          message.error(data.message);
        }
      });
    };

    //内容查询
    const handleQueryContent = () => {
      axios.get("doc/find-content/"+doc.value.id).then((response) => {
        const data = response.data;
        if (data.success) {
          editor.value.txt.html(data.content);
          //重置分页组件
        } else {
          message.error(data.message);
        }
      });
    };
// 表单内容
    const treeSelectData = ref();
    treeSelectData.value = [];
    const doc = ref();
    doc.value={};

    const modalVisible = ref(false);
    const modalLoading = ref(false);

    const editor = ref();
    // editor.config.zIndex = 0;
    // editor.create();

    const handleSave = () => {
      modalLoading.value = true;

      doc.value.content=editor.value.txt.html();
      //保存更新
      axios.post("doc/save", doc.value).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          message.success("保存成功！")
          //重新加载
          handleQuery();
        } else {
          message.error(data.message);
        }
      });
    };

    const setDisable = (treeSelectData: any, id: any) => {
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          node.disabled = true;

          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id)
            }
          }

        } else {
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id);
          }
        }
      }
    };

    //编辑
    const edit = (record: any) => {
      // 清空文本框
      editor.value.txt.html("");
      modalVisible.value = true;
      doc.value = Tool.copy(record);

      handleQueryContent();
      //不允许选择当前节点和其子节点作为父节点，会让树断开

      treeSelectData.value = Tool.copy(level1.value);
      setDisable(treeSelectData.value, record.id);
      treeSelectData.value.unshift({id: 0, name: '无'});
      // const editor = new E("#editor");
      // editor.config.zIndex = 0;
      // editor.create();
    };
    //新增
    const add = () => {
      editor.value.txt.html("");

      modalVisible.value = true;
      doc.value = {
        musicId: route.query.musicId
      };
      treeSelectData.value = Tool.copy(level1.value);

      treeSelectData.value.unshift({id: 0, name: '无'});
      // const editor = new E("#editor");
      // editor.config.zIndex = 0;
      // editor.create();
    };
    //删除
    const deleteDoc = (id: number) => {
      getDeleteIds(level1.value, id);
      axios.delete("doc/delete/" + ids.join(",")).then((response) => {
        const data = response.data;
        if (data.success) {
          //重新加载
          handleQuery();
        }
      });
    };

    const ids: Array<string> = [];
    const getDeleteIds = (treeSelectData: any, id: any) => {
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          ids.push(node.id);

          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              getDeleteIds(children, children[j].id)
            }
          }

        } else {
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            getDeleteIds(children, id);
          }
        }
      }
    };

    // ----------------富文本预览--------------
    const drawerVisible = ref(false);
    const previewHtml = ref();
    const handlePreviewContent = () => {
      const html = editor.value.txt.html();
      previewHtml.value = html;
      drawerVisible.value = true;
    };
    const onDrawerClose = () => {
      drawerVisible.value = false;
    };

    onMounted(() => {
      handleQuery();
      doc.value.musicId=route.query.musicId;
      editor.value=new E("#editor");
      editor.value.config.zIndex = 0;
      editor.value.create();
    });

    return {
      param,
      // docs,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,
      deleteDoc,

      treeSelectData,
      doc,
      modalVisible,
      modalLoading,
      handleSave,

      handlePreviewContent,
      previewHtml,
      drawerVisible,
      onDrawerClose,
    };

  }
});
</script>

<style>
/* wangeditor默认样式, 参照: http://www.wangeditor.com/doc/pages/02-%E5%86%85%E5%AE%B9%E5%A4%84%E7%90%86/03-%E8%8E%B7%E5%8F%96html.html */
/* table 样式 */
.wangeditor table {
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
}
.wangeditor table td,
.wangeditor table th {
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  padding: 3px 5px;
}
.wangeditor table th {
  border-bottom: 2px solid #ccc;
  text-align: center;
}

/* blockquote 样式 */
.wangeditor blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 5px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
.wangeditor code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
.wangeditor pre code {
  display: block;
}

/* ul ol 样式 */
.wangeditor ul, ol {
  margin: 10px 0 10px 20px;
}

/* 和antdv p冲突，覆盖掉 */
.wangeditor blockquote p {
  font-family:"YouYuan";
  margin: 20px 10px !important;
  font-size: 16px !important;
  font-weight:600;
}

/* 点赞 */
.vote-div {
  padding: 15px;
  text-align: center;
}

/* 图片自适应 */
.wangeditor img {
  max-width: 100%;
  height: auto;
}

/* 视频自适应 */
.wangeditor iframe {
  width: 100%;
  height: 400px;
}
</style>