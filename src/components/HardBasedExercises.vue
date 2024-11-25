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
                        <MenuItem name="2" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按类型练习
                        </MenuItem>                        
                        <MenuItem name="1" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按难度练习
                        </MenuItem>
                        <MenuItem name="3" @click="on_select_history">
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
            <Layout>
                <Sider hide-trigger :style="{background: '#fff'}">
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
                        <Submenu name="1" >
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Hard Choose
                                <MenuItem name="1-1" @click="clickBeginner">Beginner</MenuItem>
                                <MenuItem name="1-2" @click="clickEasy">Easy</MenuItem>
                                <MenuItem name="1-3" @click="clickIntermediate">Intermediate</MenuItem>
                                <MenuItem name="1-4" @click="clickAdvanced">Advanced</MenuItem>
                                <MenuItem name="1-5" @click="clickExpert">Expert</MenuItem>
                                <MenuItem name="1-6" @click="clickMaster">Master</MenuItem>
                            </template>
                        </Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <Breadcrumb :style="{margin: '24px 0'}">
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Hard Based Exercises</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
                        题目<br><br><br>
                        <Select v-model="prob" style="width:200px">
                            <Option v-for="item in ProblemList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                        <br><br><br>
                        <Button type="primary" @click="submit">进入</Button>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    </div>

    <Footer> </Footer>
</template>

<script>
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
    import Footer from '@/components/Footer.vue'
    import axios from 'axios';
    export default {
        components: { Editor, Toolbar, Footer },
        data () {
            return {
                ProblemList: [
                    {
                        value: 'A+B Problem',
                        label: 'A+B Problem'
                    },
                    {
                        value: 'Factorial Problem',
                        label: 'Factorial Problem'
                    },
                    {
                        value: 'Fibonacci Problem',
                        label: 'Fibonacci Problem'
                    },
                    {
                        value: 'Prime Number Problem',
                        label: 'Prime Number Problem'
                    },
                    {
                        value: 'GCD and LCM Problem',
                        label: 'GCD and LCM Problem'
                    },
                    {
                        value: 'Modular Arithmetic Problem',
                        label: 'Modular Arithmetic Problem'
                    }

                ],
                prob: '',
                hard_choose: ''
            }
        },
        methods: {
            get_problem() {
                axios.get('/api/get_hard_based_problems', {
                    params: {
                        hard_choose: this.hard_choose
                    }
                })
                .then(res => {
                    console.log(res.data);
                    this.ProblemList = res.data;
                })
                .catch(err => {
                    console.log(err);
                    this.$Message.error('Failed to get hard based problems!');
                });
            },
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
            submit(){
                if (this.prob === '') {
                    this.$Message.error('Please choose a problem!');
                    return;
                }
                localStorage.setItem('problem_choose', this.prob);
                this.$Message.info('Choose '+this.prob+' successfully');
                this.$router.push({ path: '/problem_page' });
            },
            clickBeginner(){
                this.hard_choose = 'Beginner';
                this.$Message.info('Choose Beginner successfully');
                this.get_problem();
            },
            clickEasy(){
                this.hard_choose = 'Easy';
                this.$Message.info('Choose Easy successfully');
                this.get_problem();
            },
            clickIntermediate(){
                this.hard_choose = 'Intermediate';
                this.$Message.info('Choose Intermediate successfully');
                this.get_problem();
            },
            clickAdvanced(){
                this.hard_choose = 'Advanced';
                this.$Message.info('Choose Advanced successfully');
                this.get_problem();
            },
            clickExpert(){
                this.hard_choose = 'Expert';
                this.$Message.info('Choose Expert successfully');
                this.get_problem();
            },
            clickMaster(){
                this.hard_choose = 'Master';
                this.$Message.info('Choose Master successfully');
                this.get_problem();
            }
        },
    }

</script>