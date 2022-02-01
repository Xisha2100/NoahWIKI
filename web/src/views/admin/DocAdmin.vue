<template>

  <a-layout>

    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >

      <a-row>
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
              :columns="columns"
              :row-key="record => record.id"
              :data-source="level1"
              :loading="loading"
              :pagination="false"
          >
            <template #cover="{text:cover}">
              <a-avatar shape="square" size="large" style="background-color: #87d068">
                {{ cover }}
              </a-avatar>
              <!--          <img v-if="cover" :src="cover" alt="avatar" />-->
            </template>

            <template v-slot:action="{text,record}">
              <a-space size="small">
                <a-button type="primary" @click="edit(record)">
                  编辑
                </a-button>
                <a-popconfirm
                    title="删除后不可恢复，请再次确认"
                    ok-text="是"
                    cancel-text="否"
                    @confirm="deleteDoc(record.id)"
                >
                  <a-button type="danger">
                    删除
                  </a-button>
                </a-popconfirm>
              </a-space>
            </template>
          </a-table>
        </a-col>
        <a-col :span="16">
          <a-form
              :model="doc"

          >

            <a-form-item label="名称">
              <a-input v-model:value="doc.name"/>
            </a-form-item>

            <a-form-item label="父文档">
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

            <a-form-item label="顺序">
              <a-input v-model:value="doc.sort"/>
            </a-form-item>

            <a-form-item label="内容">
              <div id='editor'></div>
            </a-form-item>

          </a-form>
        </a-col>
      </a-row>


    </a-layout-content>
  </a-layout>


</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import E from 'wangeditor';

export default defineComponent({
  name: 'AdminDoc',
  setup() {
    const route = useRoute();
    const docs = ref();
    const level1 = ref();
    const param = ref();
    param.value = {};
    // const ebooks1 = reactive({books: []});
    const loading = ref(false);
    const columns = [
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父文档',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: '顺序',
        dataIndex: 'sort'
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    const handleQuery = () => {
      loading.value = true;
      level1.value = [];
      axios.get("doc/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {

          docs.value = data.content;
          level1.value = [];
          level1.value = Tool.array2Tree(docs.value, 0);

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
    const modalVisible = ref(false);
    const modalLoading = ref(false);


    const handleModalOk = () => {
      modalLoading.value = true;
      //保存更新
      axios.post("doc/save", doc.value).then((response) => {
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          modalVisible.value = false;
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
      modalVisible.value = true;
      doc.value = Tool.copy(record);
      //不允许选择当前节点和其子节点作为父节点，会让树断开

      treeSelectData.value = Tool.copy(level1.value);
      setDisable(treeSelectData.value, record.id);
      treeSelectData.value.unshift({id: 0, name: '无'});
      const editor = new E("#editor");
      setTimeout(function () {
        editor.create();
      }, 100);
    };
      //新增
      const add = () => {
        modalVisible.value = true;
        doc.value = {
          musicId: route.query.musicId
        };
        treeSelectData.value = Tool.copy(level1.value);

        treeSelectData.value.unshift({id: 0, name: '无'});
        const editor = new E("#editor");
        setTimeout(function () {
          editor.create();
        }, 100);
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

      onMounted(() => {
        handleQuery();

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
        handleModalOk
      };

    }
  });
</script>