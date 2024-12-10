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
                            <MenuItem name="1-1" @click="click1_1">多项式求根(Polynomial Root Finding)</MenuItem>
                            <MenuItem name="1-2" @click="click1_2">因数分解(Factorization)</MenuItem>
                            <MenuItem name="1-3" @click="click1_3">最大公因数和最小公倍数(Greatest Common Divisor and Least Common Multiple)</MenuItem>
                            <MenuItem name="1-4" @click="click1_4">模运算(Modular Arithmetic)</MenuItem>
                        </Submenu>
                        <Submenu name="2">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                组合数学(Combinatorics)
                            </template>
                            <MenuItem name="2-1" @click="click2_1">排列组合(Permutations and Combinations)</MenuItem>
                            <MenuItem name="2-2" @click="click2_2">子集(Subsets)</MenuItem>
                            <MenuItem name="2-3" @click="click2_3">排列次序(Order of Permutations)</MenuItem>
                            <MenuItem name="2-4" @click="click2_4">接下来的排列(Next Permutation)</MenuItem>
                        </Submenu>
                        <Submenu name="3">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                图论(Graph Theory)
                            </template>
                            <MenuItem name="3-1" @click="click3_1">深度优先搜索(Depth-First Search)</MenuItem>
                            <MenuItem name="3-2" @click="click3_2">广度优先搜索(Breadth-First Search)</MenuItem>
                            <MenuItem name="3-3" @click="click3_3">最短路径算法(Shortest Path Algorithms)</MenuItem>
                            <MenuItem name="3-4" @click="click3_4">最小生成树(Minimum Spanning Tree)</MenuItem>
                            <MenuItem name="3-5" @click="click3_5">最大流算法(Maximum Flow Algorithms)</MenuItem>
                        </Submenu>
                        <Submenu name="4">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                动态规划(Dynamic Programming)
                            </template>
                            <MenuItem name="4-1" @click="click4_1">通用动态规划(General Dynamic Programming)</MenuItem>
                            <MenuItem name="4-2" @click="click4_2">贪心法(Greedy Method)</MenuItem>
                            <MenuItem name="4-3" @click="click4_3">状态压缩(State Compression)</MenuItem>
                        </Submenu>
                        <Submenu name="5">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                字符串处理(String Processing)
                            </template>
                            <MenuItem name="5-1" @click="click5_1">字符串匹配(String Matching)</MenuItem>
                            <MenuItem name="5-2" @click="click5_2">字符串操作(String Manipulation)</MenuItem>
                            <MenuItem name="5-3" @click="click5_3">字符串转换(String Transformation)</MenuItem>
                        </Submenu>
                        <Submenu name="6">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                计算几何(Computational Geometry)
                            </template>
                            <MenuItem name="6-1" @click="click6_1">凸包算法(Convex Hull)</MenuItem>
                            <MenuItem name="6-2" @click="click6_2">最近点对(Closest Pair of Points)</MenuItem>
                            <MenuItem name="6-3" @click="click6_3">线段-线段交点(Line Segment Intersection)</MenuItem>
                            <MenuItem name="6-4" @click="click6_4">点-线段距离(Point-Line Segment Distance)</MenuItem>
                        </Submenu>
                        <Submenu name="7">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                数据结构(Data Structures)
                            </template>
                            <MenuItem name="7-1" @click="click7_1">栈和队列(Stacks and Queues)</MenuItem>
                            <MenuItem name="7-2" @click="click7_2">链表(Linked Lists)</MenuItem>
                            <MenuItem name="7-3" @click="click7_3">树和二叉树(Trees and Binary Trees)</MenuItem>
                            <MenuItem name="7-4" @click="click7_4">哈希表(Hash Tables)</MenuItem>
                            <MenuItem name="7-5" @click="click7_5">堆和优先队列(Heaps and Priority Queues)</MenuItem>
                        </Submenu>
                        <Submenu name="8">
                            <template #title>
                                <Icon type="ios-navigate"></Icon>
                                排序(Sorting)
                            </template>
                            <MenuItem name="8-1" @click="click8_1">冒泡排序(Bubble Sort)</MenuItem>
                            <MenuItem name="8-2" @click="click8_2">选择排序(Selection Sort)</MenuItem>
                            <MenuItem name="8-3" @click="click8_3">插入排序(Insertion Sort)</MenuItem>
                            <MenuItem name="8-4" @click="click8_4">快速排序(Quick Sort)</MenuItem>
                            <MenuItem name="8-5" @click="click8_5">归并排序(Merge Sort)</MenuItem>
                        </Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <Breadcrumb :style="{margin: '24px 0'}" >
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Categorized Exercises</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
                        题目<br><br><br>
                        <Select v-model="prob" style="width:200px" placeholder="Select a problem">
                            <Option v-for="item in ProblemList" :value="item.value" :key="item.value">
                                {{ item.label }}
                            </Option>
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
    import axios from 'axios'
    export default {
        components: { Editor, Toolbar, Footer },
        data() {
            return {
                ProblemList: [], 
                prob: '',
                catogory_choose: '',
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
            submit() {
                if (!this.prob) {
                    this.$Message.error('Please choose a problem!');
                    return;
                }
                localStorage.setItem('problem_choose', this.prob); // 存储问题 ID
                this.$Message.info('Choose problem ID ' + this.prob + ' successfully');
                this.$router.push({ path: '/problem_page' }); // 跳转到问题页面
            },

            get_problem() {
                axios.post('/api/category_problem', {
                    category: this.catogory_choose // 将所选类别发送到后端
                })
                .then(res => {
                    if (res.data.result === "success") {
                        // 映射后端返回的题目列表到前端格式
                        this.ProblemList = res.data.problems.map(problem => ({
                            value: problem.problemId, // 使用问题 ID 作为唯一标识
                            label: problem.title      // 显示问题标题
                        }));
                    } else {
                        this.$Message.error('Failed to fetch problems: ' + res.data.message);
                    }
                })
                .catch(err => {
                    console.error(err);
                    this.$Message.error('Failed to get category based problems!');
                });
            },
            click1_1(){
                this.catogory_choose = 'Polynomial Root Finding';
                this.$Message.info('Choose Polynomial Root Finding successfully');
                this.get_problem();
            },
            click1_2(){
                this.catogory_choose = 'Factorization';
                this.$Message.info('Choose Factorization successfully');
                this.get_problem();
            },
            click1_3(){
                this.catogory_choose = 'Greatest Common Divisor and Least Common Multiple';
                this.$Message.info('Choose Greatest Common Divisor and Least Common Multiple successfully');
                this.get_problem();
            },
            click1_4(){
                this.catogory_choose = 'Modular Arithmetic';
                this.$Message.info('Choose Modular Arithmetic successfully');
                this.get_problem();
            },
            click2_1(){
                this.catogory_choose = 'Permutations and Combinations';
                this.$Message.info('Choose Permutations and Combinations successfully');
                this.get_problem();
            },
            click2_2(){
                this.catogory_choose = 'Subsets';
                this.$Message.info('Choose Subsets successfully');
                this.get_problem();
            },
            click2_3(){
                this.catogory_choose = 'Order of Permutations';
                this.$Message.info('Choose Order of Permutations successfully');
                this.get_problem();
            },
            click2_4(){
                this.catogory_choose = 'Next Permutation';
                this.$Message.info('Choose Next Permutation successfully');
                this.get_problem();
            },
            click3_1(){
                this.catogory_choose = 'Depth-First Search';
                this.$Message.info('Choose Depth-First Search successfully');
                this.get_problem();
            },
            click3_2(){
                this.catogory_choose = 'Breadth-First Search';
                this.$Message.info('Choose Breadth-First Search successfully');
                this.get_problem();
            },
            click3_3(){
                this.catogory_choose = 'Shortest Path Algorithms';
                this.$Message.info('Choose Shortest Path Algorithms successfully');
                this.get_problem();
            },
            click3_4(){
                this.catogory_choose = 'Minimum Spanning Tree';
                this.$Message.info('Choose Minimum Spanning Tree successfully');
                this.get_problem();
            },
            click3_5(){
                this.catogory_choose = 'Maximum Flow Algorithms';
                this.$Message.info('Choose Maximum Flow Algorithms successfully');
                this.get_problem();
            },
            click4_1(){
                this.catogory_choose = 'General Dynamic Programming';
                this.$Message.info('Choose General Dynamic Programming successfully');
                this.get_problem();
            },
            click4_2(){
                this.catogory_choose = 'Greedy Method';
                this.$Message.info('Choose Greedy Method successfully');
                this.get_problem();
            },
            click4_3(){
                this.catogory_choose = 'State Compression';
                this.$Message.info('Choose State Compression successfully');
                this.get_problem();
            },
            click5_1(){
                this.catogory_choose = 'String Matching';
                this.$Message.info('Choose String Matching successfully');
                this.get_problem();
            },
            click5_2(){
                this.catogory_choose = 'String Manipulation';
                this.$Message.info('Choose String Manipulation successfully');
                this.get_problem();
            },
            click5_3(){
                this.catogory_choose = 'String Transformation';
                this.$Message.info('Choose String Transformation successfully');
                this.get_problem();
            },
            click6_1(){
                this.catogory_choose = 'Convex Hull';
                this.$Message.info('Choose Convex Hull successfully');
                this.get_problem();
            },
            click6_2(){
                this.catogory_choose = 'Closest Pair of Points';
                this.$Message.info('Choose Closest Pair of Points successfully');
                this.get_problem();
            },
            click6_3(){
                this.catogory_choose = 'Line Segment Intersection';
                this.$Message.info('Choose Line Segment Intersection successfully');
                this.get_problem();
            },
            click6_4(){
                this.catogory_choose = 'Point-Line Segment Distance';
                this.$Message.info('Choose Point-Line Segment Distance successfully');
                this.get_problem();
            },
            click7_1(){
                this.catogory_choose = 'Stacks and Queues';
                this.$Message.info('Choose Stacks and Queues successfully');
                this.get_problem();
            },
            click7_2(){
                this.catogory_choose = 'Linked Lists';
                this.$Message.info('Choose Linked Lists successfully');
                this.get_problem();
            },
            click7_3(){
                this.catogory_choose = 'Trees and Binary Trees';
                this.$Message.info('Choose Trees and Binary Trees successfully');
                this.get_problem();
            },
            click7_4(){
                this.catogory_choose = 'Hash Tables';
                this.$Message.info('Choose Hash Tables successfully');
                this.get_problem();
            },
            click7_5(){
                this.catogory_choose = 'Heaps and Priority Queues';
                this.$Message.info('Choose Heaps and Priority Queues successfully');
                this.get_problem();
            },
            click8_1(){
                this.catogory_choose = 'Bubble Sort';
                this.$Message.info('Choose Bubble Sort successfully');
                this.get_problem();
            },
            click8_2(){
                this.catogory_choose = 'Selection Sort';
                this.$Message.info('Choose Selection Sort successfully');
                this.get_problem();
            },
            click8_3(){
                this.catogory_choose = 'Insertion Sort';
                this.$Message.info('Choose Insertion Sort successfully');
                this.get_problem();
            },
            click8_4(){
                this.catogory_choose = 'Quick Sort';
                this.$Message.info('Choose Quick Sort successfully');
                this.get_problem();
            },
            click8_5(){
                this.catogory_choose = 'Merge Sort';
                this.$Message.info('Choose Merge Sort successfully');
                this.get_problem();
            },
        },
    }

</script>
