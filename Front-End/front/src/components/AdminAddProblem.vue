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
                    <h4>Time Limit <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="timeLimitInput" editable />
                    <h4>Memory Limit <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="memoryLimitInput" editable />
                    <h4>Difficulty Level <Icon type="ios-chatbubbles-outline" /></h4>
                    <Select v-model="difficultyLevelInput" style="width:200px">
                        <Option v-for="item in difficultyLevelList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br><br>
                    <h4>Problem Category <Icon type="ios-chatbubbles-outline" /></h4>
                    <Select v-model="problemCategoryInput" style="width:200px">
                        <Option v-for="item in problemCategoryList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <br><br>
                    <Button type="primary" style="width: 200px; height: 40px;" @click="clickSubmitInput">Submit</Button>
                </Card>
                <br>
                <Card style="width:90%; left: 5%;">
                    <template #title><h2 style="color: blueviolet;">Add Test Case</h2></template>
                    <h4>Problem ID<Icon type="ios-chatbubbles-outline" /></h4>
                    <InputNumber :max="10000" :min="0" v-model="TestProblemIDInput" style="width: 600px"/><br><br>
                    <h4>Test Input<Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="TestInput" editable /><br>
                    <h4>Test Output <Icon type="ios-chatbubbles-outline" /></h4>
                    <Paragraph v-model="TestOutput" editable /><br>
                    <br>
                    <Button type="primary" style="width: 200px; height: 40px;" @click="clickSubmitTestCase">Upload</Button>
                </Card>
            </div>
            <br>
            <Card style="width:90%; left: 5%; margin-bottom: 40px">
                <template #title><h2 style="color: blueviolet;">Delete Problem</h2></template>
                <Table border :columns="columns" :data="data">
                    <template #name="{ row }">
                        <strong>{{ row.name }}</strong>
                    </template>
                     <template #prob_name="{ row }">
                    {{ row.prob_name }}
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
                    <template #timeLimit="{ row }">
                    {{ row.timeLimit }}
                    </template>
                    <template #memoryLimit="{ row }">
                    {{ row.memoryLimit }}
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
                        title: 'Problem Name',
                        slot: 'prob_name',
                        width: 200,
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
                        name:1,
                        prob_name: 'OJS-0001  A+B Problem',
                        description : 'Given two integers A and B, your task is to calculate the sum of A and B.',
                        difficulty: 'Easy',
                        category: 'Data Structure',
                        inputDescription: 'The input consists of two integers A and B (1 ≤ A, B ≤ 10000).',
                        outputDescription: 'Output the sum of A and B.',
                        timeLimit: '1000ms',
                        memoryLimit: '256MB',
                    },
                ],
                problemCategoryList: [
                    { value: 'Polynomial Root Finding', label: 'Polynomial Root Finding' },
                    { value: 'Factorization', label: 'Factorization' },
                    { value: 'Greatest Common Divisor and Least Common Multiple', label: 'Greatest Common Divisor and Least Common Multiple' },
                    { value: 'Modular Arithmetic', label: 'Modular Arithmetic' },
                    { value: 'Permutations and Combinations', label: 'Permutations and Combinations' },
                    { value: 'Subsets', label: 'Subsets' },
                    { value: 'Order of Permutations', label: 'Order of Permutations' },
                    { value: 'Next Permutation', label: 'Next Permutation' },
                    { value: 'Depth-First Search', label: 'Depth-First Search' },
                    { value: 'Breadth-First Search', label: 'Breadth-First Search' },
                    { value: 'Shortest Path Algorithms', label: 'Shortest Path Algorithms' },
                    { value: 'Minimum Spanning Tree', label: 'Minimum Spanning Tree' },
                    { value: 'Maximum Flow Algorithms', label: 'Maximum Flow Algorithms' },
                    { value: 'General Dynamic Programming', label: 'General Dynamic Programming' },
                    { value: 'Greedy Method', label: 'Greedy Method' },
                    { value: 'State Compression', label: 'State Compression' },
                    { value: 'String Matching', label: 'String Matching' },
                    { value: 'String Manipulation', label: 'String Manipulation' },
                    { value: 'String Transformation', label: 'String Transformation' },
                    { value: 'Convex Hull', label: 'Convex Hull' },
                    { value: 'Closest Pair of Points', label: 'Closest Pair of Points' },
                    { value: 'Line Segment Intersection', label: 'Line Segment Intersection' },
                    { value: 'Point-Line Segment Distance', label: 'Point-Line Segment Distance' },
                    { value: 'Stacks and Queues', label: 'Stacks and Queues' },
                    { value: 'Linked Lists', label: 'Linked Lists' },
                    { value: 'Trees and Binary Trees', label: 'Trees and Binary Trees' },
                    { value: 'Hash Tables', label: 'Hash Tables' },
                    { value: 'Heaps and Priority Queues', label: 'Heaps and Priority Queues' },
                    { value: 'Bubble Sort', label: 'Bubble Sort' },
                    { value: 'Selection Sort', label: 'Selection Sort' },
                    { value: 'Insertion Sort', label: 'Insertion Sort' },
                    { value: 'Quick Sort', label: 'Quick Sort' },
                    { value: 'Merge Sort', label: 'Merge Sort' }
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
                problemNameInput:'NewProblemAdded',
                problemDescriptionInput:'NewProblemDescription',
                problemInputDescriptionInput:'NewProblemInput',
                problemOutputDescriptionInput:'NewProblemOutput',
                timeLimitInput: '1000ms',
                memoryLimitInput: '256MB',
                difficultyLevelInput: 'Easy',
                problemCategoryInput: 'Polynomial Root Finding',
                TestProblemIDInput: 0,
                TestInput: '',
                TestOutput: '',
            }
        },
        mounted() {
            axios.get('/api/delete_problem_detail')
            .then(response => {
                console.log(response.data);
                this.data = response.data.problemList.map(prob => ({
                    name: prob.problemId,
                    prob_name: prob.title,
                    description : prob.description,
                    difficulty: prob.level,
                    category: prob.problem_catogory,
                    inputDescription: prob.input,
                    outputDescription: prob.output,
                    timeLimit: prob.time_limit,
                    memoryLimit: prob.memory_limit,
                }));
            })
            .catch(error => {
                console.log(error);
                this.$Message.error('Failed to fetch problem data from server!');
            });
        },
        methods: {
            clickSubmitTestCase() {
                axios.post('/api/add_test',{
                    problemId: this.TestProblemIDInput,
                    testInput: this.TestInput,
                    testOutput: this.TestOutput,
                })
                .then(response => {
                    console.log(response.data);
                    if (response.data.result_code==0){
                        this.$Message.success("Testing case has been add to problem successfully!!!");
                        this.TestInput="";
                        this.TestOutput="";
                    }else{
                        this.$Message.error("Failed to add testing case from server!!!");
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error("Failed to add testing case from server!!!");
                });
            },
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
                const problemIDD = this.data[index].name;
                this.data.splice(index, 1);
                axios.post('/api/delete_problem', { id: problemIDD  })
                .then(response => {
                    console.log(response.data);
                    if (response.data.result_code==0){
                        this.$Message.success("Problem has been delete successfully!!!");
                    }else{
                        this.$Message.error("Failed to delete problem from server!!!");
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error("Failed to delete problem from server!!!");
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
                // 验证 timeLimitInput 是否以 s 或 ms 结尾
                if (!(this.timeLimitInput.endsWith('s') || this.timeLimitInput.endsWith('ms'))) {
                    this.$Message.error("时间限制 (timeLimitInput) 必须以 's' 或 'ms' 结尾！");
                    return; // 终止操作
                }

                // 验证 memoryLimitInput 是否以 MB 或 B 结尾
                if (!(this.memoryLimitInput.endsWith('MB') || this.memoryLimitInput.endsWith('B'))) {
                    this.$Message.error("内存限制 (memoryLimitInput) 必须以 'MB' 或 'B' 结尾！");
                    return; // 终止操作
                }
                axios.post('/api/add_new_problem', {
                    title: this.problemNameInput,
                    description: this.problemDescriptionInput,
                    level: this.difficultyLevelInput,
                    problemCategory: this.problemCategoryInput,
                    input: this.problemInputDescriptionInput,
                    output: this.problemOutputDescriptionInput,
                    timeLimit: this.timeLimitInput,
                    memoryLimit: this.memoryLimitInput,
                })
                .then(response => {
                    console.log(response.data);
                    if (response.data.result_code==0)
                    {
                    this.$Message.success("Problem has been added successfully!!!");
                    axios.get('/api/delete_problem_detail')
                    .then(response => {
                        console.log(response.data);
                        this.data = response.data.problemList.map(prob => ({
                            name: prob.problemId,
                            prob_name: prob.title,
                            description : prob.description,
                            difficulty: prob.level,
                            category: prob.problem_catogory,
                            inputDescription: prob.input,
                            outputDescription: prob.output,
                            timeLimit: prob.time_limit,
                            memoryLimit: prob.memory_limit,
                        }));
                    })
                    .catch(error => {
                        console.log(error);
                        this.$Message.error('Failed to fetch problem data from server!');
                    });
                    }
                    else{
                        this.$Message.error("Failed to add problem to server!!!")
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error("Failed to add problem to server!!!")
                });
            }
        },
    }

</script>
