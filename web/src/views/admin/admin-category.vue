<template>
  <a-layout-content style="padding: 0 50px">
    <a-breadcrumb style="margin: 16px 0">
      <a-breadcrumb-item>Home</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <p>
          <a-form
              layout="inline"
              :model="param"
          >
            <a-form-item>
              <a-button type="primary" @click="handleQuery()">
                Search
              </a-button>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="add()">
                Add
              </a-button>
            </a-form-item>
          </a-form>
        </p>
        <a-table
            :columns="columns"
            :row-key="record => record.id"
            :data-source="level1"
            :loading="loading"
            :pagination="false">
          <template #cover="{ text: cover }">
            <img v-if="cover" :src="cover" alt="avatar" />
          </template>
          <template v-slot:action="{ text, record }">
            <a-space size="small">
              <a-button type="primary" @click="edit(record)">
                Edit
              </a-button>
              <a-popconfirm
                  title="Are you sure delete this?"
                  ok-text="Yes"
                  cancel-text="No"
                  @confirm="handleDelete(record.id)"
              >
                <a-button danger>
                  Delete
                </a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-table>
      </a-layout-content>
    </a-layout>
  </a-layout-content>

  <a-modal
      title="Category"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk">
    <a-form :model="category" :label-col="{ span : 6 }" >
      <a-form-item label="Name">
        <a-input v-model:value="category.name" />
      </a-form-item>
      <a-form-item label="FatherCategory">
        <a-input v-model:value="category.parent" />
      </a-form-item>
      <a-form-item label="Sort">
        <a-input v-model:value="category.sort" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from "ant-design-vue"; // .success .error
import { Tool } from "@/util/tool"

export default defineComponent({
      name: 'AdminCategory',
      setup() {
        const param = ref();
        param.value = {};

        const categorys = ref();
        const loading = ref(false);

        const columns = [
            {
              title: 'Name',
              dataIndex: 'name'
            },
            {
              title: 'Parent',
              key: 'parent',
              dataIndex: 'parent'
            },
            {
              title: 'Sort',
              dataIndex: 'sort'
            },
            {
              title: 'Action',
              key: 'action',
              slots: { customRender: 'action' }
            }
        ];

        /*
        一级分类树，children属性就是二级分类，还可以往下加，支持无限级
        [{
          id: "",
          name: "",
          children: [{
            id: "",
            name: "",
          }]
        }]
         */
        const level1 = ref();
        level1.value = [];

        // 数据查询
        const handleQuery = () => {
          loading.value = true;
          axios.get("/category/all").then((response) => {
            loading.value = false;
            const data = response.data;
            // 拿到后端的数据后，做一个判断
            if (data.success) {
              categorys.value = data.content; // data.content = list
              console.log("Original Array: ", categorys.value);

              level1.value = [];
              level1.value = Tool.arrayToTree(categorys.value, 0);
              console.log("Tree Structure: ", level1);
            } else {
              message.error(data.message);
            }
          });
        };

        // 表单
        const category = ref({});
        const modalVisible = ref(false);
        const modalLoading = ref(false);
        const handleModalOk = () => {
          modalLoading.value = true;
          axios.post("/category/save", category.value).then((response) => {
            modalLoading.value = false;
            const data = response.data; // data = commonResp
            if (data.success) {
              modalVisible.value = false;

              // 重新加载列表
              handleQuery();
            } else {
              message.error(data.message);
            }
          });
        };

        // Edit
        const edit = (record: any) => {
          modalVisible.value = true;
          //category.value = record;
          category.value = Tool.copy(record);
        };

        // Add
        const add = () => {
          modalVisible.value = true;
          category.value = {};
        };

        // Delete
        const handleDelete = (id: number) => {
          axios.delete("/category/delete/" + id).then((response) => {
            const data = response.data; // data = commonResp
            if (data.success) {
              // 重新加载列表
              handleQuery();
            }
          });
        };

        onMounted(() => {
          handleQuery();
        });

        return {
          // 表格的
          param,
          //categorys,
          level1,
          columns,
          loading,
          handleQuery,

          edit,
          add,
          handleDelete,

          // 表单类的
          modalVisible,
          modalLoading,
          handleModalOk,
          category
        }
      }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>