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
              <a-input v-model:value="param.name" placeholder="Name">
                <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
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
            :data-source="categorys"
            :pagination="pagination"
            :loading="loading"
            @change="handleTableChange">
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
        const pagination = ref({
          current: 1,
          pageSize: 5,
          total: 0
        });
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

        // 数据查询
        const handleQuery = (params: any) => {
          loading.value = true;
          // http://localhost:8880/category/list?page=1&size=4
          // axios.get("/category/list?page=" + params.page + "&").then((response) =>
          axios.get("/category/list", {
            params: {
              page: params.page,
              size: params.size,
              name: param.value.name
            }
          }).then((response) => {
            loading.value = false;
            const data = response.data;
            // 拿到后端的数据后，做一个判断
            if (data.success) {
              categorys.value = data.content.list; // list = pageResp.list
              // 重置分页按钮
              pagination.value.current = params.page;
              pagination.value.total = data.content.total; // total = pageResp.total
            } else {
              message.error(data.message);
            }
          });
        };
        const handleTableChange = (pagination: any) => { // 表格点击页码时触发
          console.log("看看自带的分页参数都有啥：" + pagination);
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
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
              handleQuery({
                page: pagination.value.current,
                size: pagination.value.pageSize,
              });
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
              handleQuery({
                page: pagination.value.current,
                size: pagination.value.pageSize,
              });
            }
          });
        };

        onMounted(() => {
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });
        });

        return {
          // 表格的
          param,
          categorys,
          pagination,
          columns,
          loading,
          handleTableChange,
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