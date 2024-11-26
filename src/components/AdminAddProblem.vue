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
    left: 50px;
}
.layout-top-sider1{
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
                    <div class="layout-top-sider1">
                        <MenuItem name="2" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            用户管理
                        </MenuItem>                        
                        <MenuItem name="3" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            题目修改与查看
                        </MenuItem>
                        <MenuItem name="1" @click="on_select_add">
                            <Icon type="ios-navigate"></Icon>
                            题目增加与删除
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
            <br>
            <div>
                <Card style="width:90%; left: 5%;">
                    <template #title><h2 style="color: blueviolet;">Add Problem</h2></template>
                    <h4>Problem Name <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="problemNameInput" editable />
                    <h4>Problem Description <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="problemDescriptionInput" editable />
                    <h4>Problem Input Description <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="problemInputDescriptionInput" editable />
                    <h4>Problem Output Description <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="problemOutputDescriptionInput" editable />
                    <h4>Input & Output Sample <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="problemInputOutputSampleInput" editable />
                    <h4>Time Limit <Icon type="ios-chatbubbles-outline" /></h4>
                    <InputNumber :min="0" :step="0.01" v-model="timeLimitInput" /> ms
                    <br>
                    <h4>Memory Limit <Icon type="ios-chatbubbles-outline" /></h4>
                    <InputNumber :min="0" :step="0.01" v-model="memoryLimitInput" /> MB
                    <br>
                    <h4>Difficulty Level <Icon type="ios-chatbubbles-outline" /></h4>
                    <Select v-model="difficultyLevelInput" style="width:200px">
                        <Option v-for="item in difficultyLevelList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br>
                    <h4>Problem Category <Icon type="ios-chatbubbles-outline" /></h4>
                    <Select v-model="problemCategoryInput" style="width:200px">
                        <Option v-for="item in problemCategoryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br><br>
                    <Button type="primary" style="width: 200px; height: 40px;" @click="clickSubmitInput">Submit</Button>
                </Card>
            </div>
            <br>
            <Card style="width:90%; left: 5%;">
                <template #title><h2 style="color: blueviolet;">Delete Problem</h2></template>
                <Table border :columns="columns" :data="data">
                    <template #name="{ row }">
                        <strong>{{ row.name }}</strong>
                    </template>
                    <template #description="{ row }">
                    {{ row.description }}
                    </template>
                    <template #difficulty="{ row }">
                    {{ row.difficulty }}
                    </template>
                    <template #category="{ row }">
                    {{ row.category }}
                    </template>
                    <template #inputDescription="{ row }">
                    {{ row.inputDescription }}
                    </template>
                    <template #outputDescription="{ row }">
                    {{ row.outputDescription }}
                    </template>
                    <template #inputOutputSample="{ row }">
                    {{ row.inputOutputSample }}
                    </template>
                    <template #timeLimit="{ row }">
                    {{ row.timeLimit }} ms
                    </template>
                    <template #memoryLimit="{ row }">
                    {{ row.memoryLimit }} MB
                    </template>
                    <template #action="{ row, index }">
                        <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
                        <Button type="error" size="small" @click="remove(index)">Delete</Button>
                    </template>
                </Table>
            </Card>
        </Layout>
    </div>
    <Footer> </Footer>
</template>

<script>
    import Footer from '@/components/Footer.vue'
import axios from 'axios';
    export default {
        components: { Footer },
        data () {
            return {
                columns: [
                    {
                        title: 'Problem ID',
                        slot: 'name',
                        width: 200,
                        fixed: 'left'
                    },
                    {
                        title: 'Problem Description',
                        slot: 'description',
                        width: 400,
                    },
                    {
                        title: 'Difficulty',
                        slot: 'difficulty',
                        width: 100,
                    },
                    {
                        title: 'Category',
                        slot: 'category',
                        width: 150,
                    },
                    {
                        title: 'Input Description',
                        slot: 'inputDescription',
                        width: 300,
                    },
                    {
                        title: 'Output Description',
                        slot: 'outputDescription',
                        width: 300,
                    },
                    {
                        title: 'Input & Output Sample',
                        slot: 'inputOutputSample',
                        width: 300,
                    },
                    {
                        title: 'Time Limit',
                        slot: 'timeLimit',
                        width: 100,
                    },
                    {
                        title: 'Memory Limit',
                        slot: 'memoryLimit',
                        width: 100,
                    },
                    {
                        title: 'Operation',
                        slot: 'action',
                        width: 150,
                        align: 'center',
                        fixed: 'right'
                    }
                ],
                data: [
                    {
                        name: 'OJS-0001  A+B Problem',
                        description : 'Given two integers A and B, your task is to calculate the sum of A and B.',
                        difficulty: 'Easy',
                        category: 'Data Structure',
                        inputDescription: 'The input consists of two integers A and B (1 ≤ A, B ≤ 10000).',
                        outputDescription: 'Output the sum of A and B.',
                        inputOutputSample: 'Input: 1 2\nOutput: 3',
                        timeLimit: 1000,
                        memoryLimit: 256,
                    },
                    {
                        name: 'OJS-0002  A-B Problem',
                        description : 'Given two integers A and B, your task is to calculate the difference of A and B.',
                        difficulty: 'Easy',
                        category: 'Data Structure',
                        inputDescription: 'The input consists of two integers A and B (1 ≤ A, B ≤ 10000).',
                        outputDescription: 'Output the difference of A and B.',
                        inputOutputSample: 'Input: 1 2\nOutput: -1',
                        timeLimit: 2000,
                        memoryLimit: 128,
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
                problemNameInput:'',
                problemDescriptionInput:'',
                problemInputDescriptionInput:'',
                problemOutputDescriptionInput:'',
                problemInputOutputSampleInput:'',
                timeLimitInput: 1000,
                memoryLimitInput: 256,
                difficultyLevelInput: 'Easy',
                problemCategoryInput: 'Data Structure',
            }
        },
        mounted() {
            axios.get('/api/problem_detail')
            .then(response => {
                console.log(response.data);
                this.data = response.data;
            })
            .catch(error => {
                console.log(error);
                this.$Message.error('Failed to fetch problem data from server!');
            });
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
            show (index) {
                this.$Modal.info({
                    title: 'Problem Info',
                    content: `Name：${this.data[index].name}<br><br>Description：${this.data[index].description}<br><br>Difficulty：${this.data[index].difficulty}<br><br>Category：${this.data[index].category}<br><br>Input Description：${this.data[index].inputDescription}<br><br>Output Description：${this.data[index].outputDescription}<br><br>Input & Output Sample：${this.data[index].inputOutputSample}`,
                })
            },
            remove (index) {
                this.data.splice(index, 1);
                axios.post('/api/problem_delete', { params: { id: index } })
                .then(response => {
                    console.log(response.data);
                    this.$Modal.success({
                        title: 'Delete Successfully !',
                        content: 'Problem has been delete successfully.'
                    })
                })
                .catch(error => {
                    console.log(error);
                    this.$Modal.error({
                        title: 'Delete Failed !',
                        content: 'Failed to delete problem from server.'
                    })
                });
            },
            clickSubmitInput() {
                this.$Modal.info(
                    {
                        title: 'Please Confirm Your Problem Information:',
                        content: 'Problem Name: ' + this.problemNameInput + '<br>Problem Description: ' + this.problemDescriptionInput + '<br>Problem Input Description: ' + this.problemInputDescriptionInput + '<br>Problem Output Description: ' + this.problemOutputDescriptionInput + '<br>Input & Output Sample: ' + this.problemInputOutputSampleInput + '<br>Time Limit: ' + this.timeLimitInput + ' ms' + '<br>Memory Limit: ' + this.memoryLimitInput + ' MB' + '<br>Difficulty Level: ' + this.difficultyLevelInput + '<br>Problem Category: ' + this.problemCategoryInput,
                        onOk: () => { this.handleSubmitAdd(); }
                    }
                )
            },
            handleSubmitAdd(){
                if (this.problemNameInput === '' || this.problemDescriptionInput === '' || this.problemInputDescriptionInput === '' || this.problemOutputDescriptionInput === '' || this.problemInputOutputSampleInput === '') {
                    this.$Message.error('Please fill in all the fields of a problem !');
                    return;
                }
                this.problemNameInput = '';
                this.problemDescriptionInput = '';
                this.problemInputDescriptionInput = '';
                this.problemOutputDescriptionInput = '';
                this.problemInputOutputSampleInput = '';
                this.timeLimitInput = 1000;
                this.memoryLimitInput = 256;
                this.difficultyLevelInput = 'Easy';
                this.problemCategoryInput = 'Data Structure';
                axios.post('/api/add_new_problem', {
                    name: this.problemNameInput,
                    description: this.problemDescriptionInput,
                    difficulty: this.difficultyLevelInput,
                    category: this.problemCategoryInput,
                    inputDescription: this.problemInputDescriptionInput,
                    outputDescription: this.problemOutputDescriptionInput,
                    inputOutputSample: this.problemInputOutputSampleInput,
                    timeLimit: this.timeLimitInput,
                    memoryLimit: this.memoryLimitInput,
                })
                .then(response => {
                    console.log(response.data);
                    this.data.push({
                        name: this.problemNameInput,
                        description: this.problemDescriptionInput,
                        difficulty: this.difficultyLevelInput,
                        category: this.problemCategoryInput,
                        inputDescription: this.problemInputDescriptionInput,
                        outputDescription: this.problemOutputDescriptionInput,
                        inputOutputSample: this.problemInputOutputSampleInput,
                        timeLimit: this.timeLimitInput,
                        memoryLimit: this.memoryLimitInput,
                    });
                    this.$Modal.success({
                        title: 'Add Successfully !',
                        content: 'Problem has been added successfully.'
                    })
                })
                .catch(error => {
                    console.log(error);
                    this.$Modal.error({
                        title: 'Add Failed !',
                        content: 'Failed to add problem to server.'
                    })
                });
            }
        },
    }

</script>
