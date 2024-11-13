<template>
    <br><br>
    <Result type="success" title="登录成功">
        <template #desc>
            欢迎您! 您已成功登录OJ系统。
        </template>
        <template #extra>
            <div style='text-align:center;'>登录时间：{{new Date().toLocaleString()}}</div>
        </template>
        <template #actions>
            <Button type="primary" @click="nextPage">进入主页</Button>
            <Button @click="previousPage">退出登录</Button>
        </template>
    </Result>
    <br><br>
    <Footer> </Footer>
</template>
<script>
    import Footer from '@/components/Footer.vue'
    export default {
        components: {
            Footer
        },
        methods: {
            nextPage() {
                this.$router.push({ path: '/categorized_exercises' });
            },
            previousPage() {
                this.$router.push({ path: '/' });
            }
        }
    }
</script>
