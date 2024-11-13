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
                        <Submenu name="1">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Beginner
                            </template>
                        </Submenu>
                        <Submenu name="2">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Easy
                            </template>
                        </Submenu>
                        <Submenu name="3">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Intermediate
                            </template>
                        </Submenu>
                        <Submenu name="4">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Advanced
                            </template>
                        </Submenu>
                        <Submenu name="5">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Expert
                            </template>
                        </Submenu>
                        <Submenu name="6">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                Master
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
                        编程语言<br>
                        <RadioGroup v-model="border">
                            <Radio label="Java" border></Radio>
                            <Radio label="C/C++" border></Radio>
                            <Radio label="Python" border></Radio>
                        </RadioGroup>
                        <br>
                        <br>
                        题目
                        <br>
                        <Select v-model="model" style="width:200px">
                            <Option v-for="item in ProblemList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                        <br>
                        <br>
                        你的作答
                        <br>
                        <div style="border: 1px solid #ccc">
                            <Toolbar
                            style="border-bottom: 1px solid #ccc"
                            :editor="editorRef"
                            :defaultConfig="toolbarConfig"
                            :mode="mode"
                            />
                            <Editor
                            style="height: 500px; overflow-y: hidden;"
                            v-model="valueHtml"
                            :defaultConfig="editorConfig"
                            :mode="mode"
                            @onCreated="handleCreated"
                            />
                        </div><br>
                        <Button type="primary" @click="submit">提交</Button>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    </div>
    <Modal
        v-model="modal"
        title="Submission Confirmation"
        @on-ok="ok"
        @on-cancel="cancel">
        <p>Question: {{ model }}</p>
        <p>Language: {{ border }}</p>
        <p>Programme Implementation: {{ valueHtml }}</p>
    </Modal>
    <Footer> </Footer>
</template>

<script>
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
    import Footer from '@/components/Footer.vue'
    export default {
        components: { Editor, Toolbar, Footer },
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
            submit(){
                console.log(this.model);    //题目选择
                console.log(this.border);   //编程语言选择
                console.log(this.valueHtml);    //作答程序
                this.modal = true;
            },
            ok () {
                this.$Message.info('Submitted successfully');
                this.$router.push({ path: '/judge_result' });
            },
            cancel () {
                this.$Message.info('Submission canceled');
            },
        },
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
                model: '',
                border: 'Java',
                modal: false,
            }
        }
    }

</script>