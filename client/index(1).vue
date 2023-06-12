<template>
    <div class="container">
        <Header :balance="user.balance"></Header>
        <div class="roleMenu" v-if="roleMenuVis">
            <div class="role_search_add">
                <div class="roleSearch">
                    <span class="searchIconSpan">
                        <svg-icon icon-class="search" class="search-icon" />
                    </span>
                    <el-input class="search_input" placeholder="搜索角色"></el-input>
                </div>
                <div class="roleAdd">
                    <el-button type="primary" class="addButton" icon="el-icon-plus" circle
                        @click="addRoleDialog()"></el-button>
                </div>
            </div>
            <el-menu :unique-opened="false" :collapse-transition="false" background-color="rgb(32, 33, 35)"
                text-color="#bfcbd9" active-text-color="#f4f4f5">
                <div v-for="item in roleList" :key="item.id">
                    <el-menu-item :label="item.id" :class="currentRoleId == item.id ? 'currentMenuItem' : 'elseMenuItem'"
                        @click="currentRole = item; currentHistoryId = null; history = []; if (item.id != currentRoleId) { currentRoleId = item.id; messageStart = false; websock.close(); initWebSocket(); };">
                        <img :src='item.avatar' class="avatars" style="width:40px;height:40px">
                        <span class="menuItem">{{ item.nickname }}</span>
                        <!-- <span>
                            <svg-icon icon-class="up" class="png" />
                        </span> -->
                        <span @click="updateRoleDialog(item)">
                            <svg-icon icon-class="setting" class="png" />
                        </span>
                        <span @click="deleteRole(item.id)">
                            <svg-icon icon-class="delete" class="png" />
                        </span>
                    </el-menu-item>
                </div>
            </el-menu>
        </div>
        <div :class="roleMenuVis ? 'chatMain1' : 'chatMain2'">
            <div class="chatRecords">
                <el-scrollbar ref="scroll" style="height: 100%;width: 100%;">
                    <div class="chatgptMessage">
                        <img :src='currentRole.avatar' class="avatarsChatgpt" style="width:40px;height:40px">
                        <mavon-editor :value="currentRole.greeting" class="messageSpanChatgpt" :subfield="prop.subfield"
                            :defaultOpen="prop.defaultOpen" :toolbarsFlag="prop.toolbarsFlag" :editable="prop.editable"
                            :scrollStyle="prop.scrollStyle" :previewBackground="'rgb(68,70,83)'"
                            :boxShadow="prop.boxShadow" />
                    </div>
                    <div v-for="item in history">
                        <div :class="item.role == 'assistant' ? 'chatgptMessage' : 'userMessage'">
                            <img :src="item.role == 'assistant' ? currentRole.avatar : userAvatarUrl"
                                :class="item.role == 'assistant' ? 'avatarsChatgpt' : 'avatarsUser'"
                                style="width:40px;height:40px">
                            <mavon-editor :value="item.content" v-if="item.role == 'assistant'" class="messageSpanChatgpt"
                                :subfield="prop.subfield" :defaultOpen="prop.defaultOpen" :toolbarsFlag="prop.toolbarsFlag"
                                :editable="prop.editable" :scrollStyle="prop.scrollStyle"
                                :previewBackground="'rgb(68,70,83)'" :boxShadow="prop.boxShadow" />
                            <mavon-editor :value="item.content" v-else class="messageSpanUser" :subfield="prop.subfield"
                                :defaultOpen="prop.defaultOpen" :toolbarsFlag="prop.toolbarsFlag" :editable="prop.editable"
                                :scrollStyle="prop.scrollStyle" :previewBackground="'rgb(54,52,65)'"
                                :boxShadow="prop.boxShadow" />
                        </div>
                    </div>
                </el-scrollbar>
            </div>
            <div class="chatting">
                <div class="smallBtus">
                    <span @click="roleMenuVis = !roleMenuVis">
                        <svg-icon icon-class="role" class="smallBtu" />
                    </span>
                    <span @click="history = []; currentHistoryId = '';">
                        <svg-icon icon-class="document" class="smallBtu" />
                    </span>
                    <span @click="voice = !voice">
                        <svg-icon icon-class="mute" class="smallBtu" v-if="!voice" />
                        <svg-icon icon-class="unmute" class="smallBtu" v-if="voice" />
                    </span>
                    <span>
                        <svg-icon icon-class="save" class="smallBtu" />
                    </span>
                    <span @click="historyDialog()">
                        <svg-icon icon-class="clock" class="smallBtu" />
                    </span>
                </div>
                <div class="chattingBox">
                    <el-input v-model="chatting" class="chattingInput" type="textarea" placeholder="聊点什么吧..."></el-input>
                </div>
                <div class="sendMessage">
                    <el-button type="primary" class="sendMessageBtu" @click="sendMessage()">发送</el-button>
                </div>
            </div>
        </div>
        <el-dialog title="新增" :visible.sync="addRoleDialogVis" :close-on-click-modal="false">
            <el-form>
                <el-menu :default-active="addRoleDialogAction" class="el-menu-demo" mode="horizontal"
                    @select="addRoleDialogSelect">
                    <el-menu-item index="1">信息设置</el-menu-item>
                    <el-menu-item index="2">属性设置</el-menu-item>
                </el-menu>
            </el-form>
            <br><br><br><br><br>
            <el-form :model="role" v-if="currentAddRoleDialogSelect == 1">
                <el-form-item label="头像:" :label-width="'140px'">
                    <img :src="role.avatar" style="width:35px;height: 35px;float: left;margin-right: 20px;">
                    <el-button type="primary" @click="setAvaterDialog()" style="float: left;">设置头像</el-button>
                </el-form-item>
                <el-form-item label="昵称:" :label-width="'140px'">
                    <span @click="nicknameRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.nickname" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="招呼语:" :label-width="'140px'">
                    <span @click="greetingRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.greeting" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="模型:" :label-width="'140px'">
                    <span @click="modelRemind()">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-select v-model="role.model" placeholder="请选择">
                        <el-option v-for="item in modelOptions" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色指令:" :label-width="'140px'">
                    <span @click="systemRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.system" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="是否上架应用市场:" :label-width="'140px'">
                    <el-switch v-model="role.isMarket" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                </el-form-item>
                <el-form-item label="应用商城分类:" :label-width="'140px'" v-if="role.isMarket == 1">
                    <el-select v-model="role.marketType" placeholder="请选择">
                        <el-option v-for="item in marketTypeOptions" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <el-form :model="role" v-if="currentAddRoleDialogSelect == 2">
                <el-form-item label="上下文数:" :label-width="'140px'">
                    <span @click="contextNRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.contextN" show-input :min="2" :max="36"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="最大回复:" :label-width="'140px'">
                    <span @click="maxTokensRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.maxTokens" show-input :min="1" :max="2048"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="随机属性1:" :label-width="'140px'">
                    <span @click="temperatireRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.temperature" show-input :min="0" :max="2" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="随机属性2:" :label-width="'140px'">
                    <span @click="topPRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.topP" show-input :min="0" :max="1" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="重复属性:" :label-width="'140px'">
                    <span @click="frequencyPenaltyRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.frequencyPenalty" show-input :min="-2" :max="2" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="话题属性:" :label-width="'140px'">
                    <span @click="presencePenaltyRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.presencePenalty" show-input :min="-2" :max="3" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="词汇权重:" :label-width="'140px'">
                    <span @click="logitBiasRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.logitBias" style="width:calc(100% - 15px);float:left"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addRoleDialogCancel()">取消</el-button>
                <el-button type="primary" @click="addRoleDialogConfirm()">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="设置头像" :visible.sync="setAvaterDialogVis" :close-on-click-modal="false">
            <div class="setAvater">
                <img :src='role.avatar' style="width:100%;height: 100%;">
            </div>
            <!-- <div style="width: 100%;height: 50px;">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="文字头像" name="first">文字头像</el-tab-pane>
                    <el-tab-pane label="预设头像" name="second">预设头像</el-tab-pane>
                </el-tabs>
            </div> -->
            <div>
                <div class="selectAvatarWord">
                    <el-input class="selectAvatarWordInput" v-model="wordSelect" @change="setAvaterWord()"></el-input>
                </div>
                <div class="selectAvaterColor">
                    <div v-for="item in colorSelect" class="colorSelectBox"
                        :style="{ 'background-color': '#' + item.color }" @click="setAvaterColor(item.color)">
                    </div>
                </div>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="setAvaterDialogVis = false">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改" :visible.sync="updateRoleDialogVis" :close-on-click-modal="false">
            <el-form>
                <el-menu :default-active="updateRoleDialogAction" class="el-menu-demo" mode="horizontal"
                    @select="updateRoleDialogSelect">
                    <el-menu-item index="1">信息设置</el-menu-item>
                    <el-menu-item index="2">属性设置</el-menu-item>
                </el-menu>
            </el-form>
            <br><br><br><br><br>
            <el-form :model="role" v-if="currentUpdateRoleDialogSelect == 1">
                <el-form-item label="头像:" :label-width="'140px'">
                    <img :src="role.avatar" style="width:35px;height: 35px;float: left;margin-right: 20px;">
                    <el-button type="primary" @click="setAvaterDialog()" style="float: left;">设置头像</el-button>
                </el-form-item>
                <el-form-item label="昵称:" :label-width="'140px'">
                    <span @click="nicknameRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.nickname" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="招呼语:" :label-width="'140px'">
                    <span @click="greetingRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.greeting" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="模型:" :label-width="'140px'">
                    <span @click="modelRemind()">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-select v-model="role.model" placeholder="请选择">
                        <el-option v-for="item in modelOptions" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色指令:" :label-width="'140px'">
                    <span @click="systemRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.system" style="width:calc(100% - 15px)"></el-input>
                </el-form-item>
                <el-form-item label="是否上架应用市场:" :label-width="'140px'">
                    <el-switch v-model="role.isMarket" active-color="#13ce66" inactive-color="#ff4949" v-if="onMarket"
                        disabled></el-switch>
                    <el-switch v-model="role.isMarket" active-color="#13ce66" inactive-color="#ff4949" v-else></el-switch>
                </el-form-item>
                <el-form-item label="应用商城分类:" :label-width="'140px'" v-if="role.isMarket == 1">
                    <el-select v-model="role.marketType" placeholder="请选择">
                        <el-option v-for="item in marketTypeOptions" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <el-form :model="role" v-if="currentUpdateRoleDialogSelect == 2">
                <el-form-item label="上下文数:" :label-width="'140px'"
                    v-if="role.contextN != undefined && role.contextN != '' && role.contextN != null">
                    <span @click="contextNRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.contextN" show-input :min="2" :max="36"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="最大回复:" :label-width="'140px'"
                    v-if="role.maxTokens != undefined && role.maxTokens != '' && role.maxTokens != null">
                    <span @click="maxTokensRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.maxTokens" show-input :min="1" :max="2048"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="随机属性1:" :label-width="'140px'"
                    v-if="role.temperature != undefined && role.temperature != '' && role.temperature != null">
                    <span @click="temperatureRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.temperature" show-input :min="0" :max="2" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="随机属性2:" :label-width="'140px'"
                    v-if="role.topP != undefined && role.topP != '' && role.topP != null">
                    <span @click="topPRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.topP" show-input :min="0" :max="1" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="重复属性:" :label-width="'140px'"
                    v-if="role.frequencyPenalty != undefined && role.frequencyPenalty != '' && role.frequencyPenalty != null">
                    <span @click="frequencyPenaltyRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.frequencyPenalty" show-input :min="-2" :max="2" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="话题属性:" :label-width="'140px'"
                    v-if="role.presencePenalty != undefined && role.presencePenalty != '' && role.presencePenalty != null">
                    <span @click="presencePenaltyRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-slider v-model="role.presencePenalty" show-input :min="-2" :max="3" :step="0.01"
                        style="width:calc(100% - 15px);float:left"></el-slider>
                </el-form-item>
                <el-form-item label="词汇权重:" :label-width="'140px'"
                    v-if="role.logitBias != undefined && role.logitBias != '' && role.logitBias != null">
                    <span @click="logitBiasRemind()" style="float: left;">
                        <svg-icon icon-class="exclamation" style="width:15px;height:15px" />
                    </span>
                    <el-input v-model="role.logitBias" style="width:calc(100% - 15px);float:left"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="updateRoleDialogCancel()">取消</el-button>
                <el-button type="primary" @click="updateRoleDialogConfirm()">确定</el-button>
            </div>
        </el-dialog>
        <div class="overlay" @click="historyDialogVis = false" v-if="historyDialogVis" style="z-index:1003;"></div>
        <div class="historyList" v-if="historyDialogVis" style="z-index:1003;">
            <div class="historyTitle">
                历史记录
            </div>
            <el-divider class="historyDivider"></el-divider>
            <el-menu :unique-opened="false" :collapse-transition="false" background-color="rgb(32, 33, 35)"
                text-color="#bfcbd9" active-text-color="#f4f4f5" style="height:90%;width:100%;z-index: 1003;">
                <el-scrollbar ref="scroll" style="height: 100%;width: 100%;">
                    <div v-for="item in historyList">
                        <el-menu-item class="historyItem"
                            :class="currentHistoryId == item.chatSessionId ? 'currentHistoryItem' : 'elseHistoryItem'"
                            @click="getHistory(item.chatSessionId)">
                            <div class="historyItemTitle" style="overflow: hidden;text-overflow: ellipsis">
                                {{ item.title }}
                            </div>
                            <div class="historyItemTime">
                                {{ item.createDate }}
                            </div>
                        </el-menu-item>
                    </div>
                </el-scrollbar>
            </el-menu>
        </div>
    </div>
</template>

<script>
import { getUserToken, removeUserToken } from '@/utils/localStroageUtil';
import { addRole, getRoleList, deleteRole, updateRole, getHistoryList, getHistory } from '@/api/dialogue';
import { getUserInfo } from '@/api/user';
import { formatTime } from '@/utils/myUtils'
import marketType from '@/json/marketType.json'
import Header from '@/components/header/header.vue';
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
    name: 'Main',
    components: {
        Header, mavonEditor
    },
    data() {
        return {
            roleList: [],
            historyList: [],
            role: {},
            history: [],
            user: {},
            wordSelect: 'AI',
            colorSelect: [
                {
                    color: 'FF0000'
                },
                {
                    color: 'FFC0CB'
                },
                {
                    color: 'FFA500'
                },
                {
                    color: 'FFFF00'
                },
                {
                    color: 'ADFF2F'
                },
                {
                    color: '008000'
                },
                {
                    color: '00FFFF'
                },
                {
                    color: '0000FF'
                },
                {
                    color: '800080'
                },
                {
                    color: '808080'
                }
            ],
            modelOptions: [
                {
                    value: 'gpt-3.5-turbo',
                    label: 'gpt-3.5-turbo'
                },
                {
                    value: 'gpt-4.0',
                    label: 'gpt-4.0'
                }
            ],
            marketTypeOptions: marketType,
            currentRoleId: '',
            currentRole: {},
            currentHistoryId: '',
            chatting: '',
            roleMenuVis: true,
            addRoleDialogVis: false,
            addRoleDialogAction: '1',
            currentAddRoleDialogSelect: 1,
            updateRoleDialogVis: false,
            updateRoleDialogAction: '1',
            currentUpdateRoleDialogSelect: 1,
            setAvaterDialogVis: false,
            historyDialogVis: false,
            voice: 1,
            initFlag: 0,
            userAvatarUrl: 'https://ui-avatars.com/api/?rounded=true&name=你&background=random',
            wsURL: 'ws://22d6feaf.r10.cpolar.top/chat',
            websock: null,
            messageStart: false,
            onMarket: false,
        }
    },
    created() {
        this.getRoleList()
        getUserInfo().then((res) => {
            if (res.data.code == 1) {
                this.user = res.data.data
            } else {
                this.$message.error(res.data.msg)
            }
        })
    },
    computed: {
        // 解析器配置
        prop() {
            let data = {
                subfield: false,// 单双栏模式
                defaultOpen: 'preview',//edit： 默认展示编辑区域 ， preview： 默认展示预览区域 
                editable: false,	// 是否允许编辑
                toolbarsFlag: false,
                scrollStyle: true,
                previewBackground: 'rgb(68,70,83)',
                boxShadow: false
            }
            return data
        }
    },
    mounted() {
        this.initWebSocket()
    },
    destroyed() {
        this.websock.close()
    },
    methods: {
        getRoleList() {
            getRoleList().then((res) => {
                console.log(res.data)
                if (res.data.code == 1) {
                    this.roleList = res.data.data
                    if (this.initFlag == 0) {
                        this.currentRoleId = this.roleList[0].id
                        this.currentRole = this.roleList[0]
                        this.initFlag = 1
                    }
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        addRoleDialog() {
            this.role = {
                avatar: 'https://ui-avatars.com/api/?rounded=true&name=AI&background=FF0000',
                contextN: 10,
                maxTokens: 1000,
                temperature: 1.00,
                topP: 1.00,
                frequencyPenalty: 0,
                presencePenalty: 0
            }
            this.addRoleDialogVis = true
        },
        addRoleDialogSelect(key) {
            this.currentAddRoleDialogSelect = key
            console.log(this.currentAddRoleDialogSelect)
        },
        updateRoleDialog(item) {
            this.role = item
            if (item.isMarket == true) {
                this.onMarket = true
            } else {
                this.onMarket = false
            }
            this.updateRoleDialogVis = true
        },
        updateRoleDialogSelect(key) {
            this.currentUpdateRoleDialogSelect = key
        },
        setAvaterDialog() {
            this.wordSelect = this.role.avatar.substr(this.role.avatar.indexOf('name=') + 5, this.role.avatar.indexOf('&background=') - this.role.avatar.indexOf('name=') - 5)
            this.setAvaterDialogVis = true
        },
        setAvaterWord() {
            this.role.avatar = this.role.avatar.substr(0, this.role.avatar.indexOf('name=') + 5) + this.wordSelect + this.role.avatar.substr(this.role.avatar.indexOf('&background='))
            console.log(this.role.avatar)
        },
        setAvaterColor(color) {
            this.role.avatar = this.role.avatar.substr(0, this.role.avatar.indexOf('background=') + 11) + color
            console.log(this.role.avatar)
        },
        addRoleDialogCancel() {
            this.addRoleDialogVis = false
        },
        addRoleDialogConfirm() {
            addRole(this.role).then((res) => {
                if (res.data.code == 1) {
                    this.getRoleList()
                    this.$message.success('新增成功')
                    this.addRoleDialogVis = false
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        updateRoleDialogCancel() {
            this.updateRoleDialogVis = false
        },
        updateRoleDialogConfirm() {
            updateRole(this.role).then((res) => {
                if (res.data.code == 1) {
                    this.getRoleList()
                    this.$message.success('修改成功')
                    this.updateRoleDialogVis = false
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        deleteRole(id) {
            deleteRole(id).then((res) => {
                if (res.data.code == 1) {
                    this.getRoleList()
                    this.$message.success('删除成功')
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        historyDialog() {
            getHistoryList(this.currentRoleId).then((res) => {
                if (res.data.code == 1) {
                    this.historyList = res.data.data
                    for (var i = 0; i < this.historyList.length; i++) {
                        this.historyList[i].createDate = formatTime(this.historyList[i].createDate)
                    }
                    this.historyDialogVis = true
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        getHistory(sessionId) {
            getHistory(sessionId).then((res) => {
                console.log(res)
                if (res.data.code == 1) {
                    this.currentHistoryId = sessionId
                    this.history = res.data.data.messages
                } else {
                    this.$message.error(res.data.msg)
                }
            })
        },
        sendMessage() {
            if (this.chatting == '' || this.chatting == null || this.chatting == undefined) {
                this.$message.error('请先输入内容')
            } else {
                if (this.currentHistoryId == '' || this.currentHistoryId == null || this.currentHistoryId == undefined) {
                    this.websocksend({
                        roleId: this.currentRoleId,
                        message: this.chatting
                    })
                } else {
                    this.websocksend({
                        roleId: this.currentRoleId,
                        chatSessionId: this.currentHistoryId,
                        message: this.chatting
                    })
                }
                this.history.push({
                    role: 2,
                    content: this.chatting
                })
                this.chatting = ''
            }
        },
        initWebSocket() {
            if (typeof WebSocket === "undefined") {
                return console.log("您的浏览器不支持websocket")
            }
            this.websock = new WebSocket(this.wsURL, [getUserToken()])
            this.websock.onmessage = this.websockonmessage
            this.websock.onopen = this.websockonopen
            this.websock.onerror = this.websockonerror
            this.websock.onclose = this.websockonclose
        },
        websockonopen() {
            console.log('连接成功')
        },
        websockonerror() {
        },
        websockonmessage(e) {
            console.log(e.data)
            const redata = e.data
            if (redata.startsWith('{{wbtkj_newBalance}}:') == true) {
                this.user.balance = parseInt(redata.substr(21))
            } else if (redata.startsWith('{{wbtkj_chatSessionId}}:') == true) {
                this.currentHistoryId = redata.substr(24);
            } else if (redata.startsWith('[DONE]')) {
                this.messageStart = false
            } else {
                if (this.messageStart == false) {
                    this.messageStart = true
                    this.history.push({
                        role: 'assistant',
                        content: redata
                    })
                } else {
                    this.history[this.history.length - 1].content += redata
                }
            }
        },
        websocksend(Data) {
            this.websock.send(JSON.stringify(Data))
        },
        websockonclose(e) {

        },
        nicknameRemind() {
            this.$alert('角色昵称', '详情', {
                confirmButtonText: '确定'
            });
        },
        greetingRemind() {
            this.$alert('角色招呼语', '详情', {
                confirmButtonText: '确定'
            });
        },
        modelRemind() {
            this.$alert('一人民币100点数，3.5一次消耗一点，4.0一次消耗10点', '详情', {
                confirmButtonText: '确定'
            });
        },
        systemRemind() {
            this.$alert('角色指令。此次对话的system预设指令', '详情', {
                confirmButtonText: '确定'
            });
        },
        contextNRemind() {
            this.$alert('上下文数。2-36的整数，默认10。AI回复时携带上下文的总条数，当小于16时，基本可以无限对话，值越小越节省token，但上下文的关联性会越差。值为2即一个回合（无上下文）。请根据对话长短自行调整。', '详情', {
                confirmButtonText: '确定'
            });
        },
        maxTokensRemind() {
            this.$alert('最大回复。1-2048的整数，默认1000。设置AI最大回复内容大小,会影响返回结果的长度。普通聊天建议100-300、短文生成建议500-900、长文、代码生成建议900-1200。', '详情', {
                confirmButtonText: '确定'
            });
        },
        temperatureRemind() {
            this.$alert('随机属性1。0-2的小数，默认1。通常用于生成有趣、创意性的文本。值越大回复内容越赋有多样性、创造性、随机性。设为0-根据事实回答，希望得到事实答案应该降低该参数。日常聊天建议0.5-0.8。建议随机度1和随机度2两个参数只改变一个，另一个保持默认值。', '详情', {
                confirmButtonText: '确定'
            });
        },
        topPRemind() {
            this.$alert('随机属性2。0-1的小数，默认1。通常用于生成技术文档、科学论文等需要准确性的文本。较大的值会导致生成的文本更具有多祥性，但是会栖牲一定的准确性和保真度。较低的值则会导致生成的文本更加保险和准确，但是可能会过于死板和缺乏新意。建议随机属性1和随机属性2两个参数只改变一个，另一个保持默认值。', '详情', {
                confirmButtonText: '确定'
            });
        },
        temperatireRemind() {
            this.$alert('重复属性。-2.0-2.0的小数，默认值0。值越大越能够让AI更好地避免重复使用之前说过的词汇。建议微调或不变。', '详情', {
                confirmButtonText: '确定'
            });
        },
        presencePenaltyRemind() {
            this.$alert('话题属性。-2.0-2.0的小数，默认值0。值越大越能够让AI更好地控制新话题的引入。建议微调或不变。', '详情', {
                confirmButtonText: '确定'
            });
        },
        logitBiasRemind() {
            this.$alert('词汇权重。给词汇添加-100到100的权重，表示其偏差值。像-1和1之间的值应该会轻微减少或增加选择的可能性；像-100或100这样的值会导致相关词汇的禁止或独占选择。', '详情', {
                confirmButtonText: '确定'
            });
        },
        onReady(data) {
            console.log(data)
        },
        onCopy(text) {
            console.log(text)
        },
        onPasteImage(file) {
            console.log(file)
        },
        onSave(data) {
            console.log(data)
        },
    }
}
</script>

<style scoped>
.container {
    width: 100%;
    height: 100%;
}

.roleMenu {
    transition: width 1s;
    width: 280px !important;
    height: 92%;
    z-index: 1001;
    float: left;
    background-color: rgb(54, 54, 68);
}

.role_search_add {
    width: 100%;
    height: 50px;
    background-color: rgb(32, 33, 35);
}

.roleSearch {
    width: 220px;
    height: calc(100% - 10px);
    border-radius: 20px;
    background-color: rgb(54, 54, 68);
    margin-left: 10px;
    margin-top: 3px;
    float: left;
}

.searchIconSpan {
    height: 100%;
    width: 30px;
    padding-left: 8px;
    padding-top: 6px;
    float: left;
}


.search-icon {
    height: 24px;
    width: 24px;
    color: #ffffff;
}


.search_input {
    width: calc(100% - 50px);
    height: 100%;
    border: none;
    float: left;
}

.search_input /deep/ .el-input__inner {
    height: 100%;
    background-color: rgb(54, 54, 66);
    border: none;
    color: #ffffff;
    font-size: large;
}

.roleAdd {
    margin-left: 6px;
    height: 100%;
    width: 40px;
    float: left;
}

.addButton {
    margin-top: 3px;
    width: 36px !important;
    height: 36px !important;
    background-color: rgb(54, 54, 66);
    border: none;
}

.addButton:hover {
    margin-top: 3px;
    width: 36px !important;
    height: 36px !important;
    background-color: rgb(74, 54, 66);
    border: none;
}

.el-menu {
    border: none;
    height: calc(100% - 50px);
    width: 100% !important;
    float: left;
}

.menuItem {
    width: 80px;
    height: 10px;
    font-size: 15px;
    white-space: pre-wrap;
}

.png {
    height: 20px;
    width: 20px;
    color: #ffffff;
    float: right;
    margin-top: 20px;
}

.chatMain1 {
    width: calc(100% - 280px);
    height: 92%;
    float: left;
    background-color: rgb(54, 52, 65);
}

.chatMain2 {
    width: 100%;
    height: 92%;
    float: left;
    background-color: rgb(54, 52, 65);
}

.chatRecords {
    width: 100%;
    height: 66%;
    float: left;
}

.chatting {
    width: 100%;
    height: 34%;
    float: left;
}

.smallBtus {
    width: 100%;
    height: 20%;
    background-color: rgb(63, 65, 78);
}

.chattingBox {
    width: 100%;
    height: 60%;
    background-color: rgb(63, 65, 78);
}

.chattingInput {
    width: 100%;
    height: 100%;
    border: none;
}

.chattingInput /deep/ .el-textarea__inner {
    width: 100%;
    height: 100%;
    background-color: rgb(63, 65, 78);
    border: none;
    color: #ffffff;
    resize: none;
}

.sendMessage {
    width: 100%;
    height: 20%;
    background-color: rgb(63, 65, 78);
    padding-right: 10px;
}

.sendMessageBtu {
    float: right;
}

.smallBtu {
    width: 30px;
    height: 30px;
    margin: 3px;
    color: #000000;
    cursor: pointer;
}

.history {
    width: 1000px;
    height: 100%;
    right: 0px;
    top: 0px;
}

.elseMenuItem:hover {
    background-color: rgb(77, 77, 77) !important;
}

.elseMenuItem:focus {
    background-color: rgb(77, 77, 77) !important;
}

.currentMenuItem {
    background-color: rgb(77, 77, 77) !important;
}

.overlay {
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.5;
    background-color: #000;
    z-index: 1;
}

.historyList {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    width: 250px;
    height: 100%;
    background-color: rgb(32, 33, 35);
    z-index: 2;
}

.historyTitle {
    height: 50px;
    width: 100%;
    font-size: 30px;
    padding-top: 10px;
    padding-left: 10px;
    justify-content: center;
    color: #ffffff;
}

.historyItem {
    height: 40px;
    width: 100%;
    font-size: 20px;
    float: right;
    color: #ffffff;
    line-height: 40px
}

.historyDivider {
    margin-top: 6px;
    margin-bottom: 6px;
}

.avatars {
    margin-right: 10px;
}

.currentHistoryItem {
    height: 60px;
    background-color: rgb(77, 77, 77) !important;
}

.elseHistoryItem {
    height: 60px;
}

.elseHistoryItem:hover {
    height: 60px;
    background-color: rgb(77, 77, 77) !important;
}

.chatgptMessage {
    padding-top: 20px;
    padding-bottom: 20px;
    padding-left: 100px;
    padding-right: 20px;
    width: 100%;
    height: auto;
    font-size: 16px;
    color: #ffffff;
    background-color: rgb(68, 70, 83);
    float: left;
}

.userMessage {
    padding-top: 20px;
    padding-bottom: 20px;
    padding-left: 20px;
    padding-right: 100px;
    width: 100%;
    height: auto;
    font-size: 16px;
    color: #ffffff;
    background-color: rgb(54, 52, 65);
    float: right;
}

.avatarsChatgpt {
    float: left;
    margin-right: 30px;
}

.avatarsUser {
    float: right;
    margin-left: 30px;
}

.messageSpanChatgpt {
    padding: 0px !important;
    height: auto;
    width: 50%;
    float: left;
    text-align: left;
    word-break: break-all;
    color: #000000;
}

.messageSpanUser {
    padding: 0px !important;
    height: auto;
    width: 50%;
    float: right;
    text-align: right;
    word-break: break-all;
    color: #000000;
}

.el-scrollbar /deep/ .el-scrollbar__wrap {
    overflow-x: hidden;
}

.addRoleDialogSelect {
    margin-bottom: 5px;
}

.setAvater {
    width: 80px;
    height: 80px;
    margin-left: calc(50% - 40px);
    margin-bottom: 20px;
}

.selectAvatarWord {
    width: 300px;
    height: 60px;
    margin-left: calc(50% - 150px);
}

.selectAvatarWordInput /deep/ .el-input__inner {
    width: 100%;
    text-align: center;
    font-size: 20px;
}

.selectAvaterColor {
    width: 400px;
    height: 120px;
    margin-left: calc(50% - 200px);
}

.colorSelectBox {
    width: 50px;
    height: 50px;
    margin-top: 5px;
    margin-bottom: 5px;
    margin-left: 15px;
    margin-right: 15px;
    float: left;
    cursor: pointer;
}

.historyItemTitle {
    font-size: 22px;
    height: 30px;
}

.historyItemTime {
    font-size: 5px;
    height: 5px;
}

.v-note-wrapper {
    min-height: 0px;
    min-width: 0px;
    max-width: 80%;
    height: auto !important;
    width: auto !important;
    z-index: 1 !important;
    border: 0px;
    color: #ffffff;
}

.markdown-body /deep/ pre{
    background-color: rgb(48, 50, 63) !important;
}
</style>