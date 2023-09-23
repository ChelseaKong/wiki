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
            :data-source="ebooks"
            :pagination="pagination"
            :loading="loading"
            @change="handleTableChange">
          <template #cover="{ text: cover }">
            <img v-if="cover" :src="cover" alt="avatar" />
          </template>
          <template v-slot:category="{ text, record }">
            <!-- {{text}}***{{record}} 不渲染的话，text=record，渲染的话，text就是具体的值-->
            <span>
              {{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}
            </span>
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
      <a-form-item label="Category">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label: 'name', value: 'id', children: 'children'}"
            :options="level1">
        </a-cascader>
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
import { message } from "ant-design-vue"; // .success .error
import { Tool } from "@/util/tool"

export default defineComponent({
      name: 'AdminEbook',
      setup() {
        const param = ref();
        param.value = {};

        const ebooks = ref();
        const pagination = ref({
          current: 1,
          pageSize: 5,
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
              title: 'category',
              slots: { customRender: 'category' }
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

          // empty existed data. 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑还是显示编辑前的数据
          ebooks.value = [];

          // http://localhost:8880/ebook/list?page=1&size=4
          // axios.get("/ebook/list?page=" + params.page + "&").then((response) =>
          axios.get("/ebook/list", {
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
              ebooks.value = data.content.list; // list = pageResp.list
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
        const ebook = ref();
        const modalVisible = ref(false);
        const modalLoading = ref(false);

        // array [100, 101] == 前端开发/Vue
        const categoryIds = ref();

        const handleModalOk = () => {
          modalLoading.value = true;

          ebook.value.category1Id = categoryIds.value[0];
          ebook.value.category2Id = categoryIds.value[1];

          axios.post("/ebook/save", ebook.value).then((response) => {
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
          //ebook.value = record;
          ebook.value = Tool.copy(record);
          categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
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

        // Query all category
        const level1 = ref();
        let categorys: any;
        const handleQueryCategory = () => {
          loading.value = true;
          axios.get("/category/all").then((response) => {
            loading.value = false;
            const data = response.data;
            // 拿到后端的数据后，做一个判断
            if (data.success) {
              categorys = data.content; // data.content = list
              console.log("Original Array: ", categorys);

              level1.value = [];
              level1.value = Tool.arrayToTree(categorys, 0);
              console.log("Tree Structure: ", level1);
            } else {
              message.error(data.message);
            }
          });
        }

        const getCategoryName = (cid: number) => {
          // console.log(cid)
          let result = "";
          categorys.forEach( (item: any) => {
            if (item.id === cid) {
              // return item.name; 这样不起作用
              result = item.name;
            }
          });
          return result;
        };

        onMounted(() => {
          handleQueryCategory();
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });
        });

        return {
          // 表格的
          param,
          ebooks,
          pagination,
          columns,
          loading,
          handleTableChange,
          handleQuery,
          getCategoryName,

          edit,
          add,
          handleDelete,

          // 表单类的
          modalVisible,
          modalLoading,
          handleModalOk,
          ebook,
          categoryIds,
          level1,
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