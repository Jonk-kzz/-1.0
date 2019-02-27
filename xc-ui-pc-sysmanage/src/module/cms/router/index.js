import Home from '@/module/home/page/home.vue'; 
import page_list from '@/module/cms/page/page_list.vue'; 
import template_list from '@/module/cms/template/template_list.vue'; 
export default [ {
path:'/cms', 
component:Home, 
name:'CMS内容管理', 
hidden:false, 
children:[ {path:'/cms/page', name:'页面列表', component:page_list, hidden:false},  {path:'/cms/template', name:'模板管理', component:template_list, hidden:false}

]
}
]
