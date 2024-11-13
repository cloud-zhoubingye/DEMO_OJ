<style>
.layout{
    border: 1px solid #c693eb;
    background: hsl(275, 26%, 91%);
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}
.rate-demo {
    display: flex;
    justify-content: space-between;
    align-items: center;   
}
.layout-logo{
    width: 80px;
    height: 50px;
    background: #7523ab;
    border-radius: 2px;
    float: left;
    position: relative;
    top: 5px;
    left: 30px;
}
.layout-top-sider2{
    width:550px;
    left: 80px;
    margin-left: 670px;
    color: #fff;
}
.demo-split{
    height: 200px;
    border: 1px solid #dcdee2;
}
.demo-split-pane{
    padding: 10px;
}
.demo-split-pane.no-padding{
    height: 200px;
    padding: 0;
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
                    <div class="layout-top-sider2">
                        <MenuItem name="2" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            用户管理
                        </MenuItem>                        
                        <MenuItem name="1" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            题目修改与查看
                        </MenuItem>
                        <MenuItem name="3" @click="on_select_add">
                            <Icon type="ios-navigate"></Icon>
                            题目增加与删除
                        </MenuItem>
                    </div>
                </Menu>
            </Header>

            <Card style="width:99%; left: 0.5%; top: 10px; bottom: 10px;">
                <h1>Welcome to OJS administrator management system !</h1>
                <br>
                <Select v-model="currentChoose" :filterable="isFilterable" placeholder="请选择查看的题目编号">
                    <Option v-for="item in probList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </Select>
                <br><br>
                <Card style="width:90%; left: 5%;">
                    <template #title><h1 style="color: blueviolet;">{{ currentChoose }}</h1></template>
                    <h2>Problem Description <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Paragraph v-model="problemDescription" editable />
                    <br>
                    <h2>Problem Input Description <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Paragraph v-model="problemInputDescription" editable />
                    <br>
                    <h2>Problem Output Description <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Paragraph v-model="problemOutputDescription" editable />
                    <br>
                    <h2>Input & Output Sample <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Paragraph v-model="problemInputOutputSample" editable />
                    <br>
                    <h2>Time Limit <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <InputNumber :min="0" :step="0.01" v-model="timeLimit" /> ms
                    <br><br><br>
                    <h2>Memory Limit <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <InputNumber :min="0" :step="0.01" v-model="memoryLimit" /> MB
                    <br><br><br>
                    <h2>Difficulty Level <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Select v-model="difficultyLevel" style="width:200px">
                        <Option v-for="item in difficultyLevelList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br><br><br>
                    <h2>Problem Category <Icon type="ios-chatbubbles-outline" /></h2>
                    <br>
                    <Select v-model="problemCategory" style="width:200px">
                        <Option v-for="item in problemCategoryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br><br><br><br>
                    <Button type="primary" style="margin-left: 10px; width: 200px; height: 40px;" @click="clickSubmit">Submit</Button>
                    <br><br><br><br><br>
                </Card>
                <br>
            </Card>
        </Layout>
    </div>
    <Footer> </Footer>
</template>

<script>
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
    import Footer from '@/components/Footer.vue'
    export default {
        components: { Editor, Toolbar, Footer },
        data () {
            return {
                probList: [
                    {
                        value: 'OJS-0001  A+B Problem',
                        label: 'OJS-0001  A+B Problem'
                    },
                    {
                        value: 'OJS-0002  A-B Problem',
                        label: 'OJS-0002  A-B Problem'
                    },
                    {
                        value: 'OJS-0003  A*B Problem',
                        label: 'OJS-0003  A*B Problem'
                    },
                    {
                        value: 'OJS-0004  A/B Problem',
                        label: 'OJS-0004  A/B Problem'
                    },
                    {
                        value: 'OJS-0005  A^B Problem',
                        label: 'OJS-0005  A^B Problem'
                    },
                    {
                        value: 'OJS-0006  A%B Problem',
                        label: 'OJS-0006  A%B Problem'
                    },
                    {
                        value: 'OJS-0007  A+B+C Problem',
                        label: 'OJS-0007  A+B+C Problem'
                    },
                    {
                        value: 'OJS-0008  A-B-C Problem',
                        label: 'OJS-0008  A-B-C Problem'
                    },
                    {
                        value: 'OJS-0009  A*B*C Problem',
                        label: 'OJS-0009  A*B*C Problem'
                    },
                    {
                        value: 'OJS-0010  A/B/C Problem',
                        label: 'OJS-0010  A/B/C Problem'
                    },
                ],
                problemCategoryList: [
                    {
                        value: 'Number Theory',
                        label: 'Number Theory'
                    },
                    {
                        value: 'Combinatorics',
                        label: 'Combinatorics'
                    },
                    {
                        value: 'Graph Theory',
                        label: 'Graph Theory'
                    },
                    {
                        value: 'Dynamic Programming',
                        label: 'Dynamic Programming'
                    },
                    {
                        value: 'String Processing',
                        label: 'String Processing'
                    },
                    {
                        value: 'Computational Geometry',
                        label: 'Computational Geometry'
                    },
                    {
                        value: 'Data Structure',
                        label: 'Data Structure'
                    },
                    {
                        value: 'Sorting',
                        label: 'Sorting'
                    }
                ],
                difficultyLevelList: [
                    {
                        value: 'Beginner',
                        label: 'Beginner'
                    },
                    {
                        value: 'Easy',
                        label: 'Easy'
                    },
                    {
                        value: 'Intermediate',
                        label: 'Intermediate'
                    },
                    {
                        value: 'Advanced',
                        label: 'Advanced'
                    },
                    {
                        value: 'Expert',
                        label: 'Expert'
                    },
                    {
                        value: 'Master',
                        label: 'Master'
                    }
                ],
                currentChoose: 'Problem: None',
                isFilterable: true,
                problemDescription: 'This is the description of the problem. This is the description of the problem.',
                problemInputDescription: 'This is the input description of the problem. This is the input description of the problem.',
                problemOutputDescription: 'This is the output description of the problem. This is the output description of the problem.',
                problemInputOutputSample: 'This is the input and output sample of the problem. This is the input and output sample of the problem.',
                difficultyLevel: 'Beginner',
                problemCategory: 'Number Theory',
                memoryLimit: 256,
                timeLimit: 2000,
            }
        },
        methods: {
            on_select_categorized_exercises() {
                this.$router.push({ path: '/admin_user' });
            },
            on_select_hard_based_exercises() {
                this.$router.push({ path: '/admin_problem' });
            },
            on_select_add() {
                this.$router.push({ path: '/admin_add_problem' });
            },
            handleEdit (row, index) {
                this.editName = row.name;
                this.editEmail = row.mail;
                this.editPassword = row.password;
                this.editType = row.type;
                this.editIndex = index;
            },
            handleSave (index) {
                if (this.editType != 'User' && this.editType != 'Administrator') {
                    this.$Message.error('Type must be User or Administrator');
                    return;
                }
                this.data[index].name = this.editName;
                this.data[index].mail = this.editEmail;
                this.data[index].type = this.editType;
                this.data[index].password = this.editPassword;
                this.editIndex = -1;
            },
            clickSubmit() {
                if (this.currentChoose == 'Problem: None') {
                    this.$Modal.error({
                        title: 'Submit Error !',
                        content: 'Please choose a problem index first.'
                    });
                } 
                else {
                    this.$Modal.info({
                        title: '请确认您修改的题目信息：',
                        content: `<p>题目编号：${this.currentChoose}</p>
                                  <p>题目描述：${this.problemDescription}</p>
                                  <p>输入描述：${this.problemInputDescription}</p>
                                  <p>输出描述：${this.problemOutputDescription}</p>
                                  <p>输入输出样例：${this.problemInputOutputSample}</p>
                                  <p>时间限制：${this.timeLimit}</p>
                                  <p>内存限制：${this.memoryLimit}</p>
                                  <p>难度等级：${this.difficultyLevel}</p>
                                  <p>题目类别：${this.problemCategory}</p>`,
                        onOk: () => { this.$Message.success('Submit successfully!'); }
                    });

                }
            }
        },
    }

</script>
