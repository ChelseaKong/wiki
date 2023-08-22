<template>
  <a-layout-content style="padding: 0 50px">
    <a-breadcrumb style="margin: 16px 0">
      <a-breadcrumb-item>Home</a-breadcrumb-item>
    </a-breadcrumb>
    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
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
              <a-button type="primary">
                Edit
              </a-button>
              <a-button danger>
                Delete
              </a-button>
            </a-space>
          </template>
        </a-table>
      </a-layout-content>
    </a-layout>
  </a-layout-content>
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
              pageSize: 2,
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
          axios.get("/ebook/list", params).then((response) => {
            loading.value = false;
            const data = response.data;
            ebooks.value = data.content;
            // 重置分页按钮
            pagination.value.current = params.page;
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

        onMounted(() => {
          handleQuery({});
        });

        return {
          ebooks,
          pagination,
          columns,
          loading,
          handleTableChange
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