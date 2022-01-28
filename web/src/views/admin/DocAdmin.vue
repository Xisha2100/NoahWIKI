<template>

  <a-layout>

    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
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
    </a-layout-content>
  </a-layout>

  <a-modal
      v-model:visible="modalVisible"
      title="文档表单"
      :confirm-loading="confirmModalLoading"
      @ok="handleModalOk"
  >
    <a-form
        :model="doc"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
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
            :tree-data="level1"
            :replaceFields="{label: 'name',key: 'id',value: 'id' ,children: 'children'}"
        >
        </a-tree-select>
      </a-form-item>

      <a-form-item label="父文档">
        <!--        <a-input v-model:value="doc.parent"/>-->
        <a-select
            v-model:value="doc.parent"
            ref="select"
        >
          <a-select-option value="0">
            无
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="doc.id===c.id">
            {{ c.name }}
          </a-select-option>
        </a-select>

      </a-form-item>

      <a-form-item label="顺序">
        <a-input v-model:value="doc.sort"/>
      </a-form-item>

    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminDoc',
  setup() {
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
    //编辑
    const edit = (record: any) => {
      modalVisible.value = true;
      doc.value = Tool.copy(record);
    };
    //新增
    const add = () => {
      modalVisible.value = true;
      doc.value = {};
    };

    const deleteDoc = (id: number) => {
      axios.delete("doc/delete/" + id).then((response) => {
        const data = response.data;
        if (data.success) {
          //重新加载
          handleQuery();
        }
      });
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


      doc,
      modalVisible,
      modalLoading,
      handleModalOk
    };

  }
});
</script>