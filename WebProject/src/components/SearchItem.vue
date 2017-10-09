<<style >

.item{
    border: 1 solid blue;
    margin: 0 10 20 10;
    padding: 5;
}
span{
    display:block;
}

em{
    background-color: yellow;
}
</style>

<template>
    <ul>
        <li v-for='item in hits'>
            <div class='item'>
                <span>{{item._source.filePath}}</span>
                <span>{{item._source.title}}</span>
                <div v-for='body in item.highlight.body'>
                    <span v-html='body'></span>
                </div>
            </div>
            <el-button type="primary" size="mini" @click='delConfig(item.id)'>删除</el-button> {{item.name}}
        </li>
    </ul>
</template>
<script>

import { mapState } from 'vuex'

export default {
    methods: {
        delConfig(id) {
            let self = this;

            this.ajax.post('/config/delete?id=' + id).then(result => {
                if (result.code == 0) {
                    this.info('delete success');

                    //通知更新
                    this.$store.dispatch('config/reload');
                }
                else {
                    this.error(result.msg);
                }
            })
        }
    },
    props: ['hits']
}
</script>