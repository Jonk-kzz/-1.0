<template>
	<!--编写页面静态部分 ,即view部分-->
	<div>
		<div>
			<el-row>
				<el-col :span="2">
					<div class="grid-content bg-purple">
						<el-button
							type="success"
							icon="el-icon-check"
							@click="dialogFormVisible = true,queryByTemplate()"
							circle
						>添加</el-button>
					</div>
				</el-col>

				<div>
					<span></span>
				</div>
				<el-col :span="22">
					<div class="grid-content bg-purple">
						<!--查询表单-->
						<el-form :inline="true" :model="params" class="demo-form-inline">
							<el-form-item label="页面别名">
								<el-input v-model="params.pageAliase" clearable placeholder="请输入"></el-input>
							</el-form-item>
							<el-form-item label="页面名称">
								<el-input v-model="params.pageName" clearable placeholder="请输入"></el-input>
							</el-form-item>
							<el-form-item label="页面类型">
								<el-input v-model="params.pageType" clearable placeholder="请输入"></el-input>
							</el-form-item>
							<el-form-item label="站台名称">
								<el-select v-model="params.siteId" clearable placeholder="站台名称">
									<el-option
										v-for="item in siteList"
										:key="item.siteId"
										:label="item.siteName"
										:value="item.siteId"
									></el-option>
								</el-select>
							</el-form-item>
							<el-form-item>
								<el-button icon="el-icon-search" circle v-on:click="query"></el-button>
							</el-form-item>
						</el-form>
					</div>
				</el-col>
			</el-row>

			<el-table v-bind:data="list" style="width: 100%">
				<el-table-column prop="pageName" label="页面名称" width="350"></el-table-column>
				<el-table-column prop="pageAliase" label="别名" width="120"></el-table-column>
				<el-table-column prop="pageType" label="页面类型" width="120"></el-table-column>
				<el-table-column prop="pageWebPath" label="访问路径" width="250"></el-table-column>
				<el-table-column prop="pagePhysicalPath" label="物理路径" width="320"></el-table-column>
				<el-table-column prop="pageCreateTime" label="创建时间" width="320"></el-table-column>
				<el-table-column fixed="right" label="操作" width="180">
					<template slot-scope="page">
						<el-button @click="preview(page.row.pageId)" type="text" size="small">预览</el-button>
						<el-button size="small" type="primary" plain @click="postPage(page.row.pageId)">发布页面</el-button>
						<el-button
							type="primary"
							icon="el-icon-edit"
							@click="dialogFormVisible = true,get(page.row.pageId)"
							size="small"
						>编辑</el-button>
						<el-button type="danger" icon="el-icon-delete" size="small" @click="del(page.row.pageId)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>

			<el-pagination
				background
				layout="prev, pager, next"
				v-bind:current-page="this.params.page"
				v-bind:page-size="this.params.size"
				v-on:current-change="changePage"
				v-bind:total="total"
				style="float:right;"
			></el-pagination>
		</div>

		<!--添加页面-->
		<div>
			<el-dialog title="添加模板" :visible.sync="dialogFormVisible" :before-close="close">
				<el-form :model="pageForm" :rules="pageFormRules" ref="pageForm" label-width="120px">
					<el-form-item label="所属在站点" prop="siteId">
						<el-select v-model="pageForm.siteId" clearable placeholder="请选择站点">
							<el-option
								v-for="item in siteList"
								:key="item.siteId"
								:label="item.siteName"
								:value="item.siteId"
							></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="选择模板" prop="templateId">
						<el-select v-model="pageForm.templateId" clearable placeholder="请选择">
							<el-option
								v-for="item in templateList"
								:key="item.templateId"
								:label="item.templateName"
								:value="item.templateId"
							></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="页面名称" prop="pageName">
						<el-input v-model="pageForm.pageName"></el-input>
					</el-form-item>
					<el-form-item label="别名" prop="pageAliase">
						<el-input v-model="pageForm.pageAliase"></el-input>
					</el-form-item>
					<el-form-item label="访问路径" prop="pageWebPath">
						<el-input v-model="pageForm.pageWebPath"></el-input>
					</el-form-item>
					<el-form-item label="物理路径" prop="pagePhysicalPath">
						<el-input v-model="pageForm.pagePhysicalPath"></el-input>
					</el-form-item>
					<el-form-item label="数据路径" prop="dataUrl">
						<el-input v-model="pageForm.dataUrl"></el-input>
					</el-form-item>
					<el-form-item label="类型" prop="pageType">
						<el-radio-group v-model="pageForm.pageType">
							<el-radio class="radio" label="0">静态</el-radio>
							<el-radio class="radio" label="1">动态</el-radio>
						</el-radio-group>
					</el-form-item>
					<el-form-item label="创建时间" prop="pageCreateTime">
						<el-col :span="11">
							<el-date-picker
								type="date"
								placeholder="选择日期"
								v-model="pageForm.pageCreateTime"
								style="width: 100%;"
							></el-date-picker>
						</el-col>
						<el-col class="line" :span="2">-</el-col>
						<el-col :span="11">
							<el-time-picker
								type="fixed-time"
								placeholder="选择时间"
								v-model="pageForm.pageCreateTime"
								style="width: 100%;"
							></el-time-picker>
						</el-col>
					</el-form-item>

					<el-form-item>
						<el-button type="primary" @click="addSubmit('pageForm')">立即创建</el-button>
						<el-button @click="resetForm('pageForm')">取消</el-button>
					</el-form-item>
				</el-form>
			</el-dialog>
		</div>
	</div>
</template>

<script>
import * as cmsApi from "../api/cms";

//编写页面静态部分,即model及vm部分

export default {
	data() {
		return {
			siteList: [], //站点列表
			templateList: [], //模板列表
			list: [],
			total: 50,
			params: {
				pageName: "",
				pageType: "",
				pageSite: "",
				pageAliase: "",
				page: 1,
				size: 7
			},

			pageForm: {
				pageId: "",
				siteId: "",
				templateId: "",
				pageName: "",
				pageAliase: "",
				pageWebPath: "",
				pageParameter: "",
				pagePhysicalPath: "",
				dataUrl: "",
				pageType: "",
				pageCreateTime: new Date()
			},

			// 配置验证规则
			pageFormRules: {
				siteId: [{ required: true, message: "请选择站点", trigger: "blur" }],
				templateId: [
					{ required: true, message: "请选择模板", trigger: "blur" }
				],
				pageName: [
					{ required: true, message: "请输入页面名称", trigger: "blur" }
				],
				pageAliase: [
					{ required: true, message: "请输入别名", trigger: "blur" }
				],
				pageWebPath: [
					{ required: true, message: "请输入访问路径", trigger: "blur" }
				],
				pagePhysicalPath: [
					{ required: true, message: "请输入物理路径", trigger: "blur" }
				],
				pageType: [{ required: true, message: "请选择类型", trigger: "blur" }]
			},

			dialogFormVisible: false
			//formLabelWidth: '120px'
		};
	},
	mounted: function() {
		//默认查询页面
		this.query();
		//初始化站点列表
		this.queryBySite();
	},
	methods: {
		//分页查询
		changePage: function(page) {
			this.params.page = page;
			this.query();
		},

		/**
		 * 全局查询 方法
		 */
		query: function() {
			cmsApi
				.page_list(this.params.page, this.params.size, this.params)
				.then(res => {
					this.total = res.queryResult.total;
					this.list = res.queryResult.list;
				});
		},

		/**
		 * 页面预览
		 */
		preview: function(pageId) {
			window.open("http://www.xuecheng.com/cms/preview/" + pageId);
		},
		/**
		 * 发布页面
		 */
		postPage: function(id) {
			this.$confirm("确定发布改页面吗?", "提示", {})
				.then(() => {
					cmsApi.page_postPage(id).then(res => {
						if (res.success) {
							console.log("发布页面id=" + id);
							this.$message.success("发布成功,请稍后查看结果.....");
						} else {
							this.$message.error("发布失败");
						}
					});
				})
				.catch(() => {});
		},
		//根据id查询
		get: function(pageId) {
			this.queryBySite();
			this.queryByTemplate();
			cmsApi.page_get(pageId).then(res => {
				this.pageForm = res;
			});
		},
		//删除页面
		del: function(pageId) {
			cmsApi.page_del(pageId);
			this.query();
		},
		close: function() {
			this.$refs["pageForm"].resetFields();
			this.dialogFormVisible = false;
		},

		/**
		 * 站台列表
		 */
		queryBySite: function() {
			cmsApi.find_site().then(res => {
				this.siteList = res.queryResult.list;
			});
		},
		/**
		 * 模板列表
		 */
		queryByTemplate: function() {
			cmsApi.find_template().then(res => {
				this.templateList = res.queryResult.list;
			});
		},

		/**
		 * 提交表单
		 * @param formName //表单内容
		 */
		addSubmit: function(formName) {
			this.$refs[formName].validate(valid => {
				if (valid) {
					if (this.pageForm.pageId != "") {
						this.$confirm("确定更新吗?", "提示", {}).then(() => {
							cmsApi
								.page_edit(this.pageForm.pageId, this.pageForm)
								.then(res => {
									if (res.success) {
										this.$message({
											message: "提交成功",
											type: "success"
										});
										this.query();
										//重置表单
										this.$refs[formName].resetFields();
										//退出添加表单
										this.dialogFormVisible = false;
									} else {
										this.$message.error(res.message);
									}
								});
						});
					} else {
						this.$confirm("确定提交吗?", "提示", {})
							.then(() => {
								//提交表单
								cmsApi.page_add(this.pageForm).then(res => {
									if (res.success) {
										this.$message({
											message: "提交成功",
											type: "success"
										});

										this.query();

										//重置表单
										this.$refs[formName].resetFields();
										//退出添加表单
										this.dialogFormVisible = false;
									} else {
										this.$message.error("提交失败");
									}
								});
							})
							.catch(() => {});
					}
				} else {
					return false;
				}
			});
		},

		/**
		 * 取消提交 重置 表单内容
		 * @param formName //表单内容
		 */
		resetForm: function(formName) {
			this.$refs[formName].resetFields();

			this.dialogFormVisible = false;
		}
	}
};
</script>

<style>
/**
      编写页面样式, 不是必须
   */
</style>
