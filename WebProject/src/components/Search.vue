<template>
    <div>
        <div style="margin-top: 15px; width: 600px;">
            <el-input placeholder="请输入内容" v-model="key">
                <el-select style="width: 120px;" v-model="type" slot="prepend" placeholder="请选择">
                    <el-option label="所有文件" value="all"></el-option>
                    <el-option label="文本文件" value="text"></el-option>
                </el-select>
                <el-button slot="append" icon="search" @click="doSearch"></el-button>
            </el-input>
        </div>

        <SearchItem :hits="this.hits.hits" />

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.currentPage" :page-sizes="[10, 50, 100, 500]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="hits.total">
        </el-pagination>

    </div>
</template>
<script>
import ela from '../libs/ela';
import UI from 'element-ui';

export default {
    data() {
        return {
            key: 'import',
            type: 'all',
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
            let loading = UI.Loading.service({ fullscreen: true });

            ela.searchArt(this.key, this.page).then(hits => { this.hits = hits; loading.close(); });
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