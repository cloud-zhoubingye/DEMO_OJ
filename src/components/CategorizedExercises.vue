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
    background: #7523ab;
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
                    <div class="layout-nav">
                        <MenuItem name="1" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按类型练习
                        </MenuItem>                        
                        <MenuItem name="2" @click="on_select_hard_based_exercises">
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
                <Sider hide-trigger :style="{background: '#84598d'}">
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
                        <Submenu name="1">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                数论(Number Theory)
                            </template>
                            <MenuItem name="1-1">多项式求根(Polynomial Root Finding)</MenuItem>
                            <MenuItem name="1-2">因数分解(Factorization)</MenuItem>
                            <MenuItem name="1-3">最大公因数和最小公倍数(Greatest Common Divisor and Least Common Multiple)</MenuItem>
                            <MenuItem name="1-4">模运算(Modular Arithmetic)</MenuItem>
                        </Submenu>
                        <Submenu name="2">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                组合数学(Combinatorics)
                            </template>
                            <MenuItem name="2-1">排列组合(Permutations and Combinations)</MenuItem>
                            <MenuItem name="2-2">子集(Subsets)</MenuItem>
                            <MenuItem name="2-3">排列次序(Order of Permutations)</MenuItem>
                            <MenuItem name="2-4">接下来的排列(Next Permutation)</MenuItem>
                        </Submenu>
                        <Submenu name="3">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                图论(Graph Theory)
                            </template>
                            <MenuItem name="3-1">深度优先搜索(Depth-First Search)</MenuItem>
                            <MenuItem name="3-2">广度优先搜索(Breadth-First Search)</MenuItem>
                            <MenuItem name="3-3">最短路径算法(Shortest Path Algorithms)</MenuItem>
                            <MenuItem name="3-4">最小生成树(Minimum Spanning Tree)</MenuItem>
                            <MenuItem name="3-5">最大流算法(Maximum Flow Algorithms)</MenuItem>
                        </Submenu>
                        <Submenu name="4">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                动态规划(Dynamic Programming)
                            </template>
                            <MenuItem name="4-1">通用动态规划(General Dynamic Programming)</MenuItem>
                            <MenuItem name="4-2">贪心法(Greedy Method)</MenuItem>
                            <MenuItem name="4-3">状态压缩(State Compression)</MenuItem>
                        </Submenu>
                        <Submenu name="5">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                字符串处理(String Processing)
                            </template>
                            <MenuItem name="5-1">字符串匹配(String Matching)</MenuItem>
                            <MenuItem name="5-2">字符串操作(String Manipulation)</MenuItem>
                            <MenuItem name="5-3">字符串转换(String Transformation)</MenuItem>
                        </Submenu>
                        <Submenu name="6">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                计算几何(Computational Geometry)
                            </template>
                            <MenuItem name="6-1">凸包算法(Convex Hull)</MenuItem>
                            <MenuItem name="6-2">最近点对(Closest Pair of Points)</MenuItem>
                            <MenuItem name="6-3">线段-线段交点(Line Segment Intersection)</MenuItem>
                            <MenuItem name="6-4">点-线段距离(Point-Line Segment Distance)</MenuItem>
                        </Submenu>
                        <Submenu name="7">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                数据结构(Data Structures)
                            </template>
                            <MenuItem name="7-1">栈和队列(Stacks and Queues)</MenuItem>
                            <MenuItem name="7-2">链表(Linked Lists)</MenuItem>
                            <MenuItem name="7-3">树和二叉树(Trees and Binary Trees)</MenuItem>
                            <MenuItem name="7-4">哈希表(Hash Tables)</MenuItem>
                            <MenuItem name="7-5">堆和优先队列(Heaps and Priority Queues)</MenuItem>
                        </Submenu>
                        <Submenu name="8">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                排序(Sorting)
                            </template>
                            <MenuItem name="8-1">冒泡排序(Bubble Sort)</MenuItem>
                            <MenuItem name="8-2">选择排序(Selection Sort)</MenuItem>
                            <MenuItem name="8-3">插入排序(Insertion Sort)</MenuItem>
                            <MenuItem name="8-4">快速排序(Quick Sort)</MenuItem>
                            <MenuItem name="8-5">归并排序(Merge Sort)</MenuItem>
                        </Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <Breadcrumb :style="{margin: '24px 0'}" >
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Categorized Exercises</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
                        编程语言<br>
                        <RadioGroup v-model="border">
                            <Radio label="Java" border></Radio>
                            <Radio label="C/C++" border></Radio>
                            <Radio label="Python" border></Radio>
                        </RadioGroup>
                        <br><br>
                        题目<br>
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
                valueHtml: '',
            }
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
    }

</script>
