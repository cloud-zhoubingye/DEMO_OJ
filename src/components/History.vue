<style>
.layout{
    border: 1px solid #c693eb;
    background: hsl(275, 26%, 91%);
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}
.layout-logo{
    width: 80px;
    height: 50px;
    background: #aa50e1;
    border-radius: 2px;
    float: left;
    position: relative;
    top: 5px;
    left: 30px;
}
.layout-nav{
    width: 550px;
    margin: 0 auto;
    margin-right: 20px;
}
</style>
<template>
    <div class="layout">
        <Layout>
            <Header>
                <Menu mode="horizontal" theme="dark" active-name="1">
                  <div class="layout-logo"> 
                    <img src="../../public/background/proj_icon.png" alt="logo" style="width: 100%; height: 100%;"/> 
                  </div>
                    <div class="layout-nav">
                        <MenuItem name="3" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按类型练习
                        </MenuItem>                        
                        <MenuItem name="2" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按难度练习
                        </MenuItem>
                        <MenuItem name="1" @click="on_select_history">
                            <Icon type="ios-navigate"></Icon>
                            做题记录
                        </MenuItem>
                        <MenuItem name="4" @click="on_select_person_info">
                            <Icon type="ios-navigate"></Icon>
                            个人信息
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
        </Layout>
    </div>
    <Table stripe :columns="columns" :data="data"></Table>
    <br><br>
    <Footer></Footer>
</template>
<script>
    import Footer from '@/components/Footer.vue'
import axios from 'axios';
    export default {
        components: {
            Footer
        },
        data () {
            return {
                columns: [
                    {
                        title: 'Name',
                        key: 'name'
                    },
                    {
                        title: 'Date',
                        key: 'date'
                    },
                    {
                        title: 'Question',
                        key: 'question'
                    },
                    {
                        title: 'Score',
                        key: 'score'
                    }
                ],
                data: [
                    {
                        name: 'John Brown',
                        date: '2024-10-03 18:53:15',
                        question: 'Polynomial Root Finding',
                        score: 100
                    },
                    {
                        name: 'Jim Green',
                        date: '2024-10-03 18:53:15',
                        question: 'Factorization',
                        score: 90
                    },
                    {
                        name: 'Joe Black',
                        date: '2024-10-03 18:53:15',
                        question: 'Greatest Common Divisor and Least Common Multiple',
                        score: 80
                    },
                    {
                        name: 'Jon Snow',
                        date: '2024-10-03 18:53:15',
                        question: 'Modular Arithmetic',
                        score: 70
                    }
                ]
            }
        },
        mounted() {
            axios.get('/api/history')
                .then(response => {
                    this.data = response.data;
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error('Get history failed');
                });
        },
        methods: {
            on_select_categorized_exercises() {
                this.$router.push({ path: '/categorized_exercises' });
            },
            on_select_hard_based_exercises() {
                this.$router.push({ path: '/hard_based_exercises' });
            },
            on_select_history(){
                this.$router.push({ path: '/history' });
            },
            on_select_person_info(){
                this.$router.push({ path: '/person_info' });
            },
        }
    }
</script>

