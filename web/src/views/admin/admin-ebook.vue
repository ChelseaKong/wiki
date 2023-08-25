<template>
  <a-layout-content style="padding: 0 50px">
    <a-breadcrumb style="margin: 16px 0">
      <a-breadcrumb-item>Home</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <p>
          <a-button type="primary" @click="add()" size="large">
            Add
          </a-button>
        </p>
        <a-table
            :columns="columns"
            :row-key="record => record.id"
            :data-source="ebooks"
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
      title="Ebook"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk">
    <a-form :model="ebook" :label-col="{ span : 6 }" >
      <a-form-item label="Cover">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="Name">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="Category I">
        <a-input v-model:value="ebook.category1Id" />
      </a-form-item>
      <a-form-item label="Category II">
        <a-input v-model:value="ebook.category2Id" />
      </a-form-item>
      <a-form-item label="Description">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
export default defineComponent({
      name: 'AdminEbook',
      setup() {
        const ebooks = ref();
        const pagination = ref({
          current: 1,
          pageSize: 4,
          total: 0
        });
        const loading = ref(false);

        const columns = [
            {
              title: 'Cover',
              dataIndex: 'cover',
              slots: { customRender: 'cover' }
            },
            {
              title: 'Name',
              dataIndex: 'name'
            },
            {
              title: 'Category I',
              key: 'category1Id',
              dataIndex: 'category1Id'
            },
            {
              title: 'Category II',
              dataIndex: 'category2Id'
            },
            {
              title: 'Documents Count',
              dataIndex: 'docCount'
            },
            {
              title: 'View Count',
              dataIndex: 'viewCount'
            },
            {
              title: 'Like Count',
              dataIndex: 'voteCount'
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
          // http://localhost:8880/ebook/list?page=1&size=4
          // axios.get("/ebook/list?page=" + params.page + "&").then((response) =>
          axios.get("/ebook/list", {
            params: {
              page: params.page,
              size: params.size
            }
          }).then((response) => {
            loading.value = false;
            const data = response.data;
            ebooks.value = data.content.list; // list = pageResp.list
            // 重置分页按钮
            pagination.value.current = params.page;
            pagination.value.total = data.content.total; // total = pageResp.total
          });
        };
        // 表格点击页码时触发
        const handleTableChange = (pagination: any) => {
          console.log("看看自带的分页参数都有啥：" + pagination);
          handleQuery({
            page: pagination.current,
            size: pagination.pageSize
          });
        };

        // 表单
        const ebook = ref({});
        const modalVisible = ref(false);
        const modalLoading = ref(false);
        const handleModalOk = () => {
          modalLoading.value = true;
          axios.post("/ebook/save", ebook.value).then((response) => {
            const data = response.data; // data = commonResp
            if (data.success) {
              modalVisible.value = false;
              modalLoading.value = false;

              // 重新加载列表
              handleQuery({
                page: pagination.value.current,
                size: pagination.value.pageSize,
              });
            }
          });
        };

        // Edit
        const edit = (record: any) => {
          modalVisible.value = true;
          ebook.value = record;
        };

        // Add
        const add = () => {
          modalVisible.value = true;
          ebook.value = {};
        };

        // Delete
        const handleDelete = (id: number) => {
          axios.delete("/ebook/delete/" + id).then((response) => {
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
          ebooks,
          pagination,
          columns,
          loading,
          handleTableChange,

          edit,
          add,
          handleDelete,

          // 表单类的
          modalVisible,
          modalLoading,
          handleModalOk,
          ebook
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