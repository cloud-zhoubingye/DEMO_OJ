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
    background: #921a9d;
    border-radius: 2px;
    float: left;
    position: relative;
    top: 5px;
    left: 30px;
}
.demo-split{
    height: 200px;
    border: 1px solid #dcdee2;
}
.demo-split-1{
    height: 400px;
    border: 1px solid #dcdee2;
}
.demo-split-pane{
    padding: 10px;
    height: 255px;
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
                        <MenuItem name="4" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按类型练习
                        </MenuItem>                        
                        <MenuItem name="3" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            按难度练习
                        </MenuItem>
                        <MenuItem name="2" @click="on_select_history">
                            <Icon type="ios-navigate"></Icon>
                            做题记录
                        </MenuItem>
                        <MenuItem name="1" @click="on_select_person_info">
                            <Icon type="ios-navigate"></Icon>
                            个人信息
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
        </Layout>
    </div>
    <br>
    <Card style="width:96%; left: 2%;">
        <template #title>                        
            <Space size="large" style="margin-left: 10px;">
                <Avatar icon="ios-person" size="large" /> 
                <h2>{{ username }}</h2>
            </Space>
        </template>
        <div class="demo-split">
            <Split v-model="split1">
                <template #left>
                    <img src="../../public/person/person_head.png" alt="info" style="width: 60%; height: 97%; margin-left: 20%; margin-top: 1%;"/> 
                </template>
                <template #right>
                    <Paragraph style="margin-left: 80px; margin-top: 50px;">
                        <Text strong>Congratulations, {{ username }}!</Text>
                        <br>
                        <Text strong>You've successfully completed </Text>
                        <CountUp :end="completedQuestions" :duration="10" ref="count" v-font="24" style="color: blueviolet;" />
                        <Text strong> problems out of {{ totalQuestions }} problems!</Text>
                        <br>
                        <Text strong>Your average score is </Text>
                        <CountUp :end="avg_score" :duration="10" :decimals="2" ref="count" v-font="24" style="color: blueviolet;" />
                        <Text strong> and your highest score is </Text>
                        <CountUp :end="max_score" :duration="10" :decimals="2" ref="count" v-font="24" style="color: blueviolet;" />
                        <Text strong> !</Text>
                    </Paragraph>
                </template>
            </Split>
        </div>
    </Card>
    <br>
    <Card style="width:96%; left: 2%;">
        <div class="demo-split-1">
            <Split v-model="split2">
                <template #left>
                    <Card style="width:96%; left: 2%; top: 2%;">
                        <div>
                            <Calendar :cell-height="40">
                            </Calendar>
                        </div>
                    </Card>
                </template>
                <template #right>
                    <Card style="width:96%; left: 2%; top: 2%;" >
                        <template #title>
                            <Icon type="ios-film-outline"></Icon>
                            Related Links
                        </template>
                        <template #extra>
                            <a href="#" @click.prevent="changeLimit">
                                <Icon type="ios-loop-strong"></Icon>
                                More
                            </a>
                        </template>
                        <p class="rate-demo" v-for="item in randomWebsiteList" :key="item.name">
                            <a :href="item.url" target="_blank">{{ item.name }}</a>
                            <span>
                                <Rate disabled v-model="item.rate" />{{ item.rate }}
                            </span>
                        </p>
                    </Card>
                </template>
            </Split>
        </div>
    </Card>
    <Footer></Footer>
</template>

<script>
    import Footer from '@/components/Footer.vue'
    export default {
        components: {
            Footer
        },
        data () {
            return {
                WebsiteList: [
                    {
                        name: 'LuoGu',
                        url: 'https://www.luogu.com.cn/',
                        rate: 10
                    },
                    {
                        name: 'POJ',
                        url: 'http://poj.org/',
                        rate: 10
                    },
                    {
                        name: 'CowCoder',
                        url: 'https://www.nowcoder.com/',
                        rate: 10
                    },
                    {
                        name: 'LiBreOJ',
                        url: 'https://loj.ac/',
                        rate: 10
                    },
                    {
                        name: 'CodeForce',
                        url: 'http://codeforces.com/',
                        rate: 10
                    },
                    {
                        name: '51Nod',
                        url: 'https://www.51nod.com/',
                        rate: 10
                    },
                    {
                        name: 'HihoCoder',
                        url: 'http://hihocoder.com/',
                        rate: 10
                    },
                    {
                        name: 'LeetCode',
                        url: 'https://leetcode-cn.com/',
                        rate: 10
                    },
                    {
                        name: 'HDU',
                        url: 'https://acm.hdu.edu.cn/',
                        rate: 10
                    },
                    {
                        name: 'BestCoder',
                        url: 'http://bestcoder.hdu.edu.cn/',
                        rate: 10
                    },
                    {
                        name: 'AtCoder',
                        url: 'https://atcoder.jp/',
                        rate: 10
                    },
                    {
                        name: 'AcWing',
                        url: 'https://www.acwing.com/about/',
                        rate: 10
                    },
                    {
                        name: 'CodeChef',
                        url: 'https://www.codechef.com/',
                        rate: 10
                    },
                    {
                        name: 'TopCoder',
                        url: 'https://www.topcoder.com/',
                        rate: 10
                    },
                    {
                        name: 'GoogleCodeJam',
                        url: 'https://codingcompetitions.withgoogle.com/codejam',
                        rate: 10
                    },
                    {
                        name: 'FacebookHackerCup',
                        url: 'https://www.facebook.com/codingcompetitions/hacker-cup',
                        rate: 10
                    },
                    {
                        name: 'KickStart',
                        url: 'https://codingcompetitions.withgoogle.com/kickstart',
                        rate: 10
                    },
                    {
                        name: 'HackerRank',
                        url: 'https://www.hackerrank.com/',
                        rate: 10
                    },
                    {
                        name: 'ACM Class',
                        url: 'https://acm.sjtu.edu.cn/OnlineJudge/',
                        rate: 10
                    }
                ],
                randomWebsiteList: [],
                username: localStorage.getItem('username'),
                totalQuestions: 200,
                completedQuestions: 165,
                avg_score: 82.5,
                max_score: 95.1,
                split1: 0.3,
                split2: 0.5,
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
            changeLimit () {
                function getArrayItems(arr, num) {
                    const temp_array = [];
                    for (let index in arr) {
                        temp_array.push(arr[index]);
                    }
                    const return_array = [];
                    for (let i = 0; i<num; i++) {
                        if (temp_array.length>0) {
                            const arrIndex = Math.floor(Math.random()*temp_array.length);
                            return_array[i] = temp_array[arrIndex];
                            temp_array.splice(arrIndex, 1);
                        } else {
                            break;
                        }
                    }
                    return return_array;
                }
                this.randomWebsiteList = getArrayItems(this.WebsiteList, 5);
            },
        },
        mounted () {
            this.changeLimit();
        }
    }
</script>
