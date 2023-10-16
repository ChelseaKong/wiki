<template>
  <!--
  <div class="home">
    <a-button type="primary" danger ghost>Danger</a-button>
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>
  </div>
  -->
  <a-layout-content style="padding: 0 50px">
    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
            @click="handleClick"
        >
          <a-menu-item key="welcome">
            <router-link to="'/'">
              <MailOutlined />
              <span> Welcome! </span>
            </router-link>
          </a-menu-item>
          <a-sub-menu v-for="item in level1" :key="item.id">
            <template v-slot:title>
              <span>
                <UserOutlined />
                {{item.name}}
              </span>
            </template>
            <a-menu-item v-for="child in item.children" :key="child.id">
              <MailOutlined />
              <span>
                {{child.name}}
              </span>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-list item-layout="vertical" size="large" :grid="{ gutter: 20, column: 3 }" :data-source="ebooks">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                <span v-for="{ icon, text } in actions" :key="icon">
                <component :is="icon" style="margin-right: 8px" />
                {{ text }}
                </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, reactive, toRef } from 'vue';
import axios from 'axios';
import { LikeOutlined, MessageOutlined, StarOutlined, MailOutlined, UserOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import { Tool } from "@/util/tool";

// const listData: any = [];
// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://joeschmoe.io/api/v1/random',
//     description:
//         'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//         'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }

export default defineComponent({
  name: 'Home',
  setup() {
    //console.log("setup");
    const ebooks = ref(); // ref用来定义响应式数据
    const ebooks1 = reactive({books: []}); // books是自己定义的属性，属性值用来放电子书列表

    const level1 = ref();
    let categorys: any;

    // query all categorys
    const handleQueryCategory = () => {
      axios.get("/category/all").then( (response) => {
        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("Original Array: ", categorys);

          level1.value = [];
          level1.value = Tool.arrayToTree(categorys, 0);
          console.log("Tree Structure: ", level1);
        } else {
          message.error(data.message);
        }
      });
    };

    const handleClick = () => {
      console.log("menu click");
    };

    onMounted( () => { // 初始化的逻辑都写到 onMounted方法里， setup就放一些参数定义、方法定义
      handleQueryCategory();
      //console.log("onMounted");
      axios.get("/ebook/list", {
        params: {
          page: 1,
          size: 1000 // 最大查一千条每页
        }
      }).then((response) => { // == function (response) {}
        const data = response.data;
        ebooks.value = data.content.list;
        //ebooks1.books = data.content;
        //console.log(response);
      });
    })

    return {
      ebooks,
      // ebooks1Books: toRef(ebooks1, "books"),
      // listData,
      pagination : {
        onChange: (page: any) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions: [
        { icon: StarOutlined, text: '156' },
        { icon: LikeOutlined, text: '156' },
        { icon: MessageOutlined, text: '2' }
      ],

      handleClick,
      level1,
    }
  },
  components: {
    MailOutlined,
    UserOutlined
  }
});
</script>

<!-- scoped表示这里的样式只在当前组件起作用 - 图标大小 -->
<style scoped>
  .ant-avatar {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border-radius: 8%;
    margin: 5px 0;
  }
</style>