<template>
    <GlobalFooter :links="links" :copyright="copyright" />
    <Button type="primary" @click="exit" style="width: 10%; margin-left: 45%;">Exit Login</Button>
    <br><br><br>
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
        methods: {
            exit () {
                localStorage.removeItem('username')
                this.$router.push('/')
            }
        }
    }
</script>
