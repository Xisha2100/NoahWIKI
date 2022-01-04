<template>

  <a-layout>

    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add()" size="large">
          新增
        </a-button>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="music"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
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
            <a-button type="danger">
              删除
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      v-model:visible="modalVisible"
      title="音乐表单"
      :confirm-loading="confirmModalLoading"
      @ok="handleModalOk"
  >
    <a-form
        :model="musicForm"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 18 }"
    >
      <a-form-item label="封面">
        <a-input v-model:value="musicForm.cover"/>
      </a-form-item>

      <a-form-item label="作者">
        <a-input v-model:value="musicForm.author"/>
      </a-form-item>

      <a-form-item label="名称">
        <a-input v-model:value="musicForm.name"/>
      </a-form-item>

      <a-form-item label="分类一">
        <a-input v-model:value="musicForm.category1Id"/>
      </a-form-item>

      <a-form-item label="分类二">
        <a-input v-model:value="musicForm.category2Id"/>
      </a-form-item>

      <a-form-item label="描述">
        <a-input v-model:value="musicForm.description" type="textarea"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>


<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'AdminMusic',
  setup() {
    const music = ref();
    // const ebooks1 = reactive({books: []});
    const pagination = ref({
      current: 1,
      pageSize: 3,
      total: 0
    });
    const loading = ref(false);

    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: {customRender: 'cover'}
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '作者',
        dataIndex: 'author'
      },
      {
        title: '分类一',
        key: 'category1Id',
        dataIndex: 'category1Id'
      },
      {
        title: '分类二',
        dataIndex: 'category2Id'
      },
      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '聆听数',
        dataIndex: 'listenCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("music/list", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        music.value = data.content.list;

        //重置分页组件
        pagination.value.current = params.page;
        pagination.value.total = data.content.total;
      });
    };

    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };
// 表单内容
    const musicForm = ref();
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      //保存更新
      axios.post("music/save", musicForm.value).then((response) => {
        const data=response.data;
        if(data.success){
          modalVisible.value = false;
          modalLoading.value = false;
          //重新加载
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        }
        });
    };
    const edit = (record: any) => {
      modalVisible.value = true;
      musicForm.value = record;
    };
    //新增
    const add = () => {
      modalVisible.value = true;
      musicForm.value = {};
    };


    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize
      });
    });

    return {
      music,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,
      add,

      musicForm,
      modalVisible,
      modalLoading,
      handleModalOk
    };

  }
});
</script>