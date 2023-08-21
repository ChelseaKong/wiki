<template>
  <!--
  <div class="home">
    <a-button type="primary" danger ghost>Danger</a-button>
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/>
  </div>
  -->
  <a-layout-content style="padding: 0 50px">
    <a-breadcrumb style="margin: 16px 0">
      <a-breadcrumb-item>Home</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            mode="inline"
            style="height: 100%"
        >
          <!-- v-model:selectedKeys="selectedKeys2"
          v-model:openKeys="openKeys" -->
          <a-sub-menu key="sub1">
            <template #title>
                <span>
                  <user-outlined />
                  subnav 111111
                </span>
            </template>
            <a-menu-item key="1">option1</a-menu-item>
            <a-menu-item key="2">option2</a-menu-item>
            <a-menu-item key="3">option3</a-menu-item>
            <a-menu-item key="4">option4</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <template #title>
                <span>
                  <laptop-outlined />
                  subnav 2
                </span>
            </template>
            <a-menu-item key="5">option5</a-menu-item>
            <a-menu-item key="6">option6</a-menu-item>
            <a-menu-item key="7">option7</a-menu-item>
            <a-menu-item key="8">option8</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub3">
            <template #title>
                <span>
                  <notification-outlined />
                  subnav 3
                </span>
            </template>
            <a-menu-item key="9">option9</a-menu-item>
            <a-menu-item key="10">option10</a-menu-item>
            <a-menu-item key="11">option11</a-menu-item>
            <a-menu-item key="12">option12</a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <pre>
          {{ebooks}}
          {{ebooks1Books}}
        </pre>
      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, reactive, toRef } from 'vue';
import axios from 'axios';
export default defineComponent({
  name: 'Home',
  setup() {
    console.log("setup");
    const ebooks = ref(); // ref用来定义响应式数据
    const ebooks1 = reactive({books: []}); // books是自己定义的属性，属性值用来放电子书列表

    onMounted( () => { // 初始化的逻辑都写到 onMounted方法里， setup就放一些参数定义、方法定义
      console.log("onMounted");
      axios.get("http://localhost:8880/ebook/list?name=Spring").then((response) => { // == function (response) {}
        const data = response.data;
        ebooks.value = data.content;
        ebooks1.books = data.content;
        console.log(response);
      });
    })

    return {
      ebooks,
      ebooks1Books: toRef(ebooks1, "books")
    }
  }
});
</script>
