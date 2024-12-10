<template>
    <div>
        <div class="header-bar">
            <span class="header-text">Problem Practice</span>
            <div class="header-buttons">
                <Button class="header-button" @click="goToCategorized">返回分题型练习</Button>
                <Button class="header-button" @click="goToHardBased">返回分难度练习</Button>
            </div>
        </div>
        <br>
        <div class="demo-split" style="width: 96%; margin-left: 2%; margin-right: 2%; height: 1000px;">
            <Split v-model="split">
                <template #left>
                    <Card style="width:96%; left: 2%; margin-right: 2%; margin-top: 2%;">
                        <template #title><h2 style="color: purple;">Problem View</h2></template>
                        <h4>Problem Name <Icon type="ios-chatbubbles-outline" /></h4>
                        <Paragraph v-model="problemTitle" />
                        <h4>Problem Description <Icon type="ios-chatbubbles-outline" /></h4>
                        <Paragraph v-model="problemDescription" />
                        <h4>Problem Input Description <Icon type="ios-chatbubbles-outline" /></h4>
                        <Paragraph v-model="problemInputDescription" />
                        <h4>Problem Output Description <Icon type="ios-chatbubbles-outline" /></h4>
                        <Paragraph v-model="problemOutputDescription" />
                        <h4>Input & Output Sample <Icon type="ios-chatbubbles-outline" /></h4>
                        <Paragraph v-model="problemInputOutputSample" />
                        <h4>Time Limit <Icon type="ios-chatbubbles-outline" /></h4>
                        <Text>{{timeLimit}}</Text>
                        <br><br>
                        <h4>Memory Limit <Icon type="ios-chatbubbles-outline" /></h4>
                        <Text>{{memoryLimit}}</Text>
                        <br><br>
                        <h4>Difficulty Level <Icon type="ios-chatbubbles-outline" /></h4>
                        <Text>{{ difficultyLevel }}</Text>
                        <br><br>
                        <h4>Problem Category <Icon type="ios-chatbubbles-outline" /></h4>
                        <Text>{{ problemCategory }}</Text>
                        <br>
                    </Card>
                    <Divider style="color: purple;">Result</Divider>
                    <Card style="width:96%; left: 2%; margin-right: 2%; margin-top: 2%;">
                        <template #title><h2 style="color: purple;">Judge Result</h2></template>
                        <h4>Result Score <Icon type="ios-arrow-dropdown" /></h4>
                        <Text>{{ resultScore }}</Text>
                        <br><br>
                        <h4>Test Case Information<Icon type="ios-arrow-dropdown" /></h4>
                        <Table border :columns="resultColumns" :data="resultData"></Table>
                        <br>
                    </Card>
                </template>
                <template #right>
                        <div style="margin-left: 25px;">
                            <Divider style="color: purple;">Settings</Divider>
                            <Card style="width:96%; left: 2%; margin-right: 2%; margin-top: 2%;">
                                <template #title><h2 style="color: purple;"> Langauge </h2></template>
                                <RadioGroup v-model="language" >
                                    <Radio label="C++"> C/C++ </Radio>
                                    <Radio label="Java"> Java </Radio>
                                    <Radio label="Python"> Python </Radio>
                                </RadioGroup>
                            </Card>
                            <Divider style="color: purple;">Code</Divider>
                            <Card style="width:96%; left: 2%; margin-right: 2%; margin-top: 2%;">
                                <template #title><h2 style="color: purple;"> Answer </h2></template>
                                <Input v-model="code" type="textarea" :autosize="{minRows: 25,maxRows: 500}" placeholder="Enter your code here..." />
                                <br><br>
                                <Button type="primary" @click="submitCode">提交</Button>
                            </Card>
                        </div>
                    
                </template>
            </Split>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
import { onMounted } from 'vue';

    export default {
        data () {
            return {
                split: 0.5,
                language: '',
                problemName: localStorage.getItem('problem_choose'),
                problemDescription: '',
                problemInputDescription: '',
                problemOutputDescription: '',
                problemInputOutputSample: '',
                timeLimit: 0,
                memoryLimit: 0,
                difficultyLevel: '',
                problemCategory: '',
                timeCost: 0,
                memoryCost: 0,
                resultScore: 0,
                resultInformation: '',
                code: '',
                problemTitle: '',
                resultColumns: [
                    {
                        title: 'ID',
                        key: 'id',
                    },
                    {
                        title: 'Time Cost',
                        key: 'timeCost',
                    },
                    {
                        title: 'Result Score',
                        key: 'resultScore',
                    },
                    {
                        title: 'Result Info',
                        key: 'resultInfo',
                    },
                ],
                resultData: [],
            }
        },
        methods: {
            goToCategorized() {
                this.$router.push('/categorized_exercises')
            },
            goToHardBased() {
                this.$router.push('/hard_based_exercises')
            },
            submitCode() {
                if (this.language === '') {
                    this.$Message.error('Please select a programming language!');
                    return;
                }
                if (this.code.trim() === '') {
                    this.$Message.error('Please enter your code!');
                    return;
                }

                const postData = {
                    prob: this.problemName,
                    language: this.language,
                    code: this.code,
                    username: localStorage.getItem('username'),
                };
                axios.post('/api/sendData' , postData, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => {
                    const data = response.data;
                    this.resultScore = data.resultScore;
                    this.resultData = data.resultData.map(res=>({
                        id: res.id,
                        timeCost: res.timeCost,
                        resultScore: res.resultScore,
                        resultInfo: res.resultInformation,
                    }))
                    console.log(this.resultData);
                    this.$Message.success('Code submitted successfully!');
                })
                .catch(error => {
                    this.resultInformation = error;
                    this.$Message.error('Failed to submit code!');
                });
            },
        },
        mounted() {
            axios.post('/api/getProblem', {
                    prob: this.problemName
            })
            .then(response => {
                const data = response.data;
                this.problemDescription = data.problemDescription;
                this.problemInputDescription = data.problemInputDescription;
                this.problemOutputDescription = data.problemOutputDescription;
                const sample = data.problemInputOutputSample;
                if (sample) {
                    this.problemInputOutputSample = `Input: ${sample.input}\nOutput: ${sample.output}`;
                } else {
                    this.problemInputOutputSample = 'No Input/Output Sample available.';
                }
                this.timeLimit = data.timeLimit;
                this.memoryLimit = data.memoryLimit;
                this.difficultyLevel = data.difficultyLevel;
                this.problemCategory = data.problemCategory;
                this.problemTitle = data.problemName;
            })
            .catch(error => {
                this.$Message.error('Failed to get ' + this.problemName + '\'s information!');
            });
        }
    }
</script>

<style>
    .header-bar {
        background-color: purple;
        padding: 10px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: white;
    }
    .header-text {
        font-weight: bold;
        font-size: 25px;
    }
    .header-buttons {
        display: flex;
        gap: 10px;
    }
    .header-button {
        background-color: lavender;
        color: white;
        border: none;
        padding: 5px 10px;
        cursor: pointer;
        font-size: 14px;
        border-radius: 5px;
    }
    .header-button:hover {
        background-color: lightpurple;
    }
    .demo-split {
        height: 200px;
        border: 1px solid #dcdee2;
    }
</style>
