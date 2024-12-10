<template>
    <GlobalFooter :links="links" :copyright="copyright" />
    <br>
    <Button type="primary" style="margin-left: 42.5%; width: 15%" @click="exitLogin">Exit</Button>
</template>
<script>
    export default {
        data () {
            return {
                links: [
                    {
                        key: '帮助',
                        title: '帮助',
                        href: 'https://www.scu.edu.cn/',
                        blankTarget: true
                    },
                    {
                        key: 'github',
                        icon: 'logo-github',
                        href: 'https://github.com/',
                        blankTarget: true
                    },
                    {
                        key: '反馈',
                        title: '反馈',
                        href: 'https://jwc.scu.edu.cn/',
                        blankTarget: true
                    }
                ],
                copyright: '24Fall SE_lab F&Z&Z'
            }
        },
        methods:{
            exitLogin(){
                this.$router.push({ path: '/' });
                localStorage.clear();
            }
        }
    }
</script>
