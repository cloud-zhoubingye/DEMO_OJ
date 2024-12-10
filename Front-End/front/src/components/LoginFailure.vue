<template>
    <br><br>
    <Result type="error" title="提交失败">
        <template #desc>
            请核对并修改以下信息后，再重新提交。
        </template>
        <template #extra>
            <div>您提交的内容有如下错误：</div>
            <div>
                <Icon type="ios-close-circle-outline" color="#ed4014" />
                您的账户或密码不正确
                <a class="ivu-ml-16" @click="clickResetPassword">
                    忘记密码请找回密码 <Icon type="ios-arrow-forward" />
                </a>
            </div>
            <div>
                <Icon type="ios-close-circle-outline" color="#ed4014" />
                您的验证码输入不正确
                <a class="ivu-ml-16" @click="tryAgain">
                    再次尝试 <Icon type="ios-arrow-forward" />
                </a>
            </div>
        </template>
        <template #actions>
            <Button type="primary"  @click="backToHome">返回</Button>
        </template>
    </Result>
    <br><br>
    <Footer> </Footer>
</template>
<script>
    import Footer from '@/components/Footer.vue'
import ResetPassword from './ResetPassword.vue';
    export default {
        components: {
            Footer
        },
        methods: {
            tryAgain() {
                this.$router.push({ path: '/login' });
            },
            backToHome() {
                this.$router.push({ path: '/' });
            },
            clickResetPassword() {
                this.$router.push({ path: '/reset_password' });
            }
        }
    }
</script>
