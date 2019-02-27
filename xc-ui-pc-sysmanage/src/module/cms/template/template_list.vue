<template>
	<div>
		<div>
			<el-row>
				<el-col :span="2">
					<div class="grid-content bg-purple">
						<el-button
							type="success"
							icon="el-icon-check"
							@click="dialogFormVisible = true,queryBySite()"
							circle
						>添加</el-button>
					</div>
				</el-col>
				<el-col :span="22">
					<div class="grid-content bg-purple-light">
						<!--查询表单-->
						<el-form :inline="true" :model="params" class="demo-form-inline">
							<el-form-item label="模板名称">
								<el-input v-model="params.templateName" clearable placeholder="请输入"></el-input>
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

			<el-table :data="list" border style="width: 100%">
				<el-table-column fixed prop="templateName" label="模板名称" width="150"></el-table-column>
				<el-table-column prop="templateParameter" label="模板参数" width="120"></el-table-column>
				<el-table-column label="操作" width="100">
					<template slot-scope="scope">
						<el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
						<el-button type="text" size="small">编辑</el-button>
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
				style="float:left;"
			></el-pagination>
		</div>

		<div>
			<el-dialog title="模板增加" :visible.sync="dialogFormVisible" :before-close="close">
				<el-form
					:model="templateForm"
					:rules="templateFormRules"
					ref="templateForm"
					label-width="120px"
				>
					<el-form-item label="所属在站点" prop="siteId">
						<el-select v-model="templateForm.siteId" clearable placeholder="请选择站点">
							<el-option
								v-for="item in siteList"
								:key="item.siteId"
								:label="item.siteName"
								:value="item.siteId"
							></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="模板名称" prop="templateName">
						<el-input v-model="templateForm.templateName"></el-input>
					</el-form-item>
					<el-form-item label="模板参数" prop="templateParameter">
						<el-input v-model="templateForm.templateParameter"></el-input>
					</el-form-item>
					<el-form-item label="上传模板" prop="templateFileId">
						<el-upload
							class="upload-demo"
							ref="upload"
							action="http://localhost:31001/cms/template/upload"
							name="ftl"
							:on-preview="handlePreview"
							:on-remove="handleRemove"
							:file-list="fileList"
							:auto-upload="false"
						>
							<el-button slot="trigger" size="small" type="primary">选取文件</el-button>
							<el-button
								style="margin-left: 10px;"
								size="small"
								type="success"
								@click="submitUpload"
							>上传到服务器</el-button>
							<div slot="tip" class="el-upload__tip">只能上传ftl文件，且不超过500kb</div>
						</el-upload>
					</el-form-item>
					<el-form-item label="创建时间" prop="templateCreateTime">
						<el-col :span="11">
							<el-date-picker
								type="date"
								placeholder="选择日期"
								v-model="templateForm.templateCreateTime"
								style="width: 100%;"
							></el-date-picker>
						</el-col>
						<el-col class="line" :span="2">-</el-col>
						<el-col :span="11">
							<el-time-picker
								type="fixed-time"
								placeholder="选择时间"
								v-model="templateForm.templateCreateTime"
								style="width: 100%;"
							></el-time-picker>
						</el-col>
					</el-form-item>

					<el-form-item>
						<el-button type="primary" @click="addSubmit('pageForm')">立即创建</el-button>
						<el-button @click="resetForm('templateForm')">取消</el-button>
					</el-form-item>
				</el-form>
			</el-dialog>
		</div>
	</div>
</template>

<script>
import * as cmsApi from "../api/cms.js";
export default {
	data() {
		return {
			list: [],
			templateFiles: [],
			siteList: [],
			total: 50,
			params: {
				templateName: "",
				templateId: "",
				siteId: "",
				page: 1,
				size: 10
			},
			templateForm: {
				siteId: "",
				templateName: "",
				templateParamate: "",
				templateFileId: "",
				templateCreateTime: ""
			},
			dialogFormVisible: false,
			templateFormRules: {},
			fileList: [
				{
					name: "food.jpeg",
					url:
						"https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
				},
				{
					name: "food2.jpeg",
					url:
						"https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
				}
			]
		};
	},

	mounted: function() {
		this.query();
		this.queryByFiles();
		this.queryBySite();
	},
	methods: {
		changePage: function(page) {
			this.params.page = page;
			this.query();
		},

		/**
		 * 分页查询
		 */
		query: function() {
			cmsApi
				.template_list(this.params.page, this.params.size, this.params)
				.then(res => {
					this.total = res.queryResult.total;
					this.list = res.queryResult.list;
				});
		},
		/**
		 * 模板文件信息
		 */
		queryByFiles: function() {
			cmsApi.template_files().then(res => {
				this.templateFiles = res;
			});
		},
		close: function() {
			this.$refs["templateForm"].resetFields();
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
		submitUpload() {
			this.$refs.upload.submit();
		},
		handleRemove(file, fileList) {
			console.log(file, fileList);
		},
		handlePreview(file) {
			console.log(file);
		},

		addSubmit: function(formName) {},

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
</style>


