<template>
    <div>
        <el-form :inline="true" :model="form" class="demo-form-inline">
            <el-form-item label="关键字">
                <el-input v-model="form.key" placeholder=""></el-input>
            </el-form-item>
                        <el-form-item>
                <el-button type="primary" @click="doSearch">添加</el-button>
            </el-form-item>
        </el-form>


        <SearchItem :hits="this.hits.hits"/>

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.currentPage" :page-sizes="[10, 50, 100, 500]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="hits.total">
        </el-pagination>


    </div>
</template>
<script>
import ela from '../libs/ela';

export default {
    data() {
        return {
            form: {
                key: 'import',
            },
            page: {
                currentPage: 1,
                pageSize: 10
            },
            hits: {
                hits: [],
                total: 2,
            }
        }
    },
    methods: {
        doSearch() {
            ela.searchArt(this.form.key, this.page).then(hits => this.hits = hits);
        }
        ,
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.page.pageSize = val;

            this.doSearch();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.page.currentPage = val;

            this.doSearch();
        }
    }
}
</script>