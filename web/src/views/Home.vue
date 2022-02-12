<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
          <MailOutlined/>
          <span>欢迎</span>
        </a-menu-item>

        <a-sub-menu v-for="item in level1" :key="item.id" :disabled="false">
          <template v-slot:title>
            <span><user-outlined/>{{ item.name }}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
            <MailOutlined/>
            <span>{{ child.name }}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout style="padding: 0 24px 24px">
      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <div class="welcome" v-show="isShowWelcome">
          <h1>欢迎光临NoahWiki</h1>
        </div>

        <a-list v-show="!isShowWelcome" item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3}"
                :data-source="music">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
            <component :is="type" style="margin-right: 8px"/>
            {{ text }}
          </span>
              </template>

              <a-list-item-meta :description="item.description">
                <template #title>
                  <router-link to="'/doc?musicId='+item.id">
                    {{ item.name }}
                  </router-link>
                </template>
                <template #avatar>
                  <a-avatar shape="square" size="large">
                    {{ item.cover }}
                  </a-avatar>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>

      </a-layout-content>

    </a-layout>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'Home',
  setup() {
    const music = ref();

    const actions: Record<string, string>[] = [
      {type: 'StarOutlined', text: '152'},
      {type: 'LikeOutlined', text: '156'},
      {type: 'MessageOutlined', text: '2'},
    ];

    const level1 = ref();
    let categorys: any;
    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      axios.get("/category/all").then((response) => {
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组：", categorys);

          // 加载完分类后，将侧边栏全部展开
          // openKeys.value = [];
          // for (let i = 0; i < categorys.length; i++) {
          //   openKeys.value.push(categorys[i].id)
          // }

          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形结构：", level1.value);
          axios.get("/music/all").then((response) => {
            const data = response.data;
            music.value = data.content;
          });
        } else {
          message.error(data.message);
        }
      });
    };

    const handleQueryMusic = () => {
      axios.get("/music/list", {
        params: {
          page: 1,
          size: 20,
          categoryId2: categoryId2,
        }
      }).then((response)=>{
        const data=response.data;
        music.value=data.content.list;
      });
    };

    const isShowWelcome = ref(true);
    let categoryId2 = 0;

    const handleClick = (value: any) => {
      console.log("click menu")
      if (value.key === 'welcome') {
        isShowWelcome.value = true;
      } else {
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQueryMusic();
      }
    };

    onMounted(() => {
      handleQueryCategory();

    });

    return {
      music,
      level1,
      actions,
      isShowWelcome,

      handleClick,
    };

  }
});
</script>
