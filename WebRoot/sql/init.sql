create database yjw_shop_v1 character set utf8;

use yjw_shop_v1;

/* 商品品牌 */
create table yjw_brand(
	brand_id bigint primary key auto_increment,
	brand_name varchar(255),
	brand_type int,
	brand_order int,
	brand_logo varchar(255),
	brand_url varchar(255),
	brand_introduction longtext,
	brand_create_time datetime,
	brand_modify_time datetime
);

/* 商品规格 */
create table yjw_specification(
	sp_id bigint primary key auto_increment,
	sp_name varchar(255),
	sp_type int,
	sp_memo varchar(255),
	sp_order int,
	sp_create_time datetime,
	sp_modify_time datetime
);

/* 商品规格值 */
create table yjw_specification_value(
	spv_id bigint primary key auto_increment,
	spv_name varchar(255),
	spv_image varchar(255),
	spv_order int,
	spv_create_time datetime,
	spv_modify_time datetime,
	spv_sp_id bigint,
	constraint spv_fk foreign key (spv_sp_id) references yjw_specification(sp_id)
);

/* 商品分类 */
create table yjw_product_category(
	pc_id bigint primary key auto_increment,
	pc_name varchar(255),
	pc_grade int,
	pc_seo_title varchar(255),
	pc_seo_keywords varchar(255),
	pc_seo_description varchar(255),
	pc_order int,
	pc_tree_path varchar(255),
	pc_is_hot bit, /*热销商品*/
	pc_is_new bit, /*新品上市*/
	pc_create_time datetime,
	pc_modify_time datetime,
	pc_parent_id bigint
);

/* 商品分类-品牌-中间表 */
create table yjw_product_category_brand(
	pcb_pc_id bigint,
	pcb_brand_id bigint,
	constraint pcb_pk primary key (pcb_pc_id,pcb_brand_id),
	constraint pcb_fk1 foreign key (pcb_pc_id) references yjw_product_category(pc_id),
	constraint pcb_fk2 foreign key (pcb_brand_id) references yjw_brand(brand_id)
);

/* 商品参数 */
create table yjw_parameter(
	param_id bigint primary key auto_increment,
	param_name varchar(255),
	param_order int,
	param_create_time datetime,
	param_modify_time datetime,
	param_pc_id bigint,
	constraint param_fk foreign key (param_pc_id) references yjw_product_category(pc_id)
);

/* 商品参数项 */
create table yjw_parameter_item(
	pitem_id bigint primary key auto_increment,
	pitem_name varchar(255),
	pitem_order int,
	pitem_create_time datetime,
	pitem_modify_time datetime,
	pitem_param_id bigint,
	constraint pitem_fk foreign key (pitem_param_id) references yjw_parameter(param_id)
);

/* 商品属性 */
create table yjw_attribute(
	attr_id bigint primary key auto_increment,
	attr_name varchar(255),
	attr_order int,
	attr_create_time datetime,
	attr_modify_time datetime,
	attr_pc_id bigint,
	constraint attr_fk foreign key (attr_pc_id) references yjw_product_category(pc_id)
);

/* 商品属性值 */
create table yjw_attribute_option(
	atopt_id bigint primary key auto_increment,
	atopt_option varchar(255),
	atopt_attr_id bigint,
	constraint atopt_fk foreign key (atopt_attr_id) references yjw_attribute(attr_id)
);

/*---------------------------------------------*/

/* 商品 */
create table yjw_product(
	p_id bigint primary key auto_increment,
	p_name varchar(255),
	p_sn varchar(255),
	p_price decimal,
	p_cost decimal,
	p_market_price decimal,
	p_image varchar(255),
	p_unit varchar(255),
	p_weight int,
	p_stock int,
	p_stock_memo varchar(255),
	p_order int,
	p_memo varchar(255),
	p_keyword varchar(255),
	p_seo_title varchar(255),
	p_seo_keywords varchar(255),
	p_seo_description varchar(255),
	p_introduction longtext,
	p_is_hot bit, /*热销商品*/
	p_is_new bit, /*新品上市*/
	p_is_recmd bit, /*推荐商品*/
	p_is_marketable bit, /*是否上架*/
	p_is_list bit, /*是否列出*/
	p_is_top bit, /*是否置顶*/
	p_is_gift bit, /*是否为赠品*/
	p_create_time datetime,
	p_modify_time datetime,
	p_pc_id bigint,
	p_brand_id bigint,
	constraint p_fk1 foreign key (p_pc_id) references yjw_product_category(pc_id),
	constraint p_fk2 foreign key (p_brand_id) references yjw_brand(brand_id)
);

/* 商品图片 */
create table yjw_product_image(
	pimg_id bigint primary key auto_increment,
	pimg_title varchar(255),
	pimg_order int,
	pimg_image varchar(255),
	pimg_p_id bigint,
	constraint pimg_fk foreign key (pimg_p_id) references yjw_product(p_id)
);

/* 商品标签 
create table yjw_product_tag(
	ptag_id bigint primary key auto_increment,
	ptag_name varchar(255),
	ptag_icon varchar(255),
	ptag_memo varchar(255),
	ptag_order int,
	ptag_create_time datetime,
	ptag_modify_time datetime
);*/

/* 商品-标签-中间表 
create table yjw_product_product_tag(
	pptag_p_id bigint,
	pptag_ptag_id bigint,
	constraint pptag_pk primary key (pptag_p_id,pptag_ptag_id),
	constraint pptag_fk1 foreign key (pptag_p_id) references yjw_product(p_id),
	constraint pptag_fk2 foreign key (pptag_ptag_id) references yjw_product_tag(ptag_id)
);*/

/* 商品-商品规格-中间表 */
create table yjw_product_specification(
	psp_p_id bigint,
	psp_sp_id bigint,
	constraint psp_pk primary key (psp_p_id,psp_sp_id),
	constraint psp_fk1 foreign key (psp_p_id) references yjw_product(p_id),
	constraint psp_fk2 foreign key (psp_sp_id) references yjw_specification(sp_id)
);

/* 商品-商品参数项-中间表 */
create table yjw_product_parameter_item_value(
	ppitem_p_id bigint,
	ppitem_pitem_id bigint,
	ppitem_value varchar(255),
	constraint ppitem_pk primary key (ppitem_p_id,ppitem_pitem_id),
	constraint ppitem_fk1 foreign key (ppitem_p_id) references yjw_product(p_id),
	constraint ppitem_fk2 foreign key (ppitem_pitem_id) references yjw_parameter_item(pitem_id)
);

/* 商品-属性值-中间表 */
create table yjw_product_attribute_option(
	patopt_p_id bigint,
	patopt_atopt_id bigint,
	constraint papopt_pk primary key (patopt_p_id,patopt_atopt_id),
	constraint papopt_fk1 foreign key (patopt_p_id) references yjw_product(p_id),
	constraint papopt_fk2 foreign key (patopt_atopt_id) references yjw_attribute_option(atopt_id)
);

/* 商品子表 */
create table yjw_product_sub(
	psub_id bigint primary key auto_increment,
	psub_p_id bigint,
	constraint psub_fk1 foreign key (psub_p_id) references yjw_product(p_id)
);

/* 商品子表-商品规格值-中间表 */
create table yjw_product_sub_specification_value(
	psspv_psub_id bigint,
	psspv_spv_id bigint,
	constraint psspv_pk primary key (psspv_psub_id,psspv_spv_id),
	constraint psspv_fk1 foreign key (psspv_psub_id) references yjw_product_sub(psub_id),
	constraint psspv_fk2 foreign key (psspv_spv_id) references yjw_specification_value(spv_id)
);


/*---------------------------------------------*/
/* 文章分类 */
create table yjw_article_category(
	ac_id bigint primary key auto_increment,
	ac_name varchar(255),
	ac_grade int,
	ac_seo_title varchar(255),
	ac_seo_keywords varchar(255),
	ac_seo_description varchar(255),
	ac_order int,
	ac_tree_path varchar(255),
	ac_create_time datetime,
	ac_modify_time datetime,
	ac_parent_id bigint
);

/* 文章 */
create table yjw_article(
	a_id bigint primary key auto_increment,
	a_title varchar(255),
	a_author varchar(255),
	a_type int,
	a_image varchar(255),
	a_content longtext,
	a_is_publish bit,
	a_is_top bit,
	a_order int,
	a_hits bigint,
	a_seo_title varchar(255),
	a_seo_keywords varchar(255),
	a_seo_description varchar(255),
	a_create_time datetime,
	a_modify_time datetime,
	a_ac_id bigint,
	constraint a_fk1 foreign key (a_ac_id) references yjw_article_category(ac_id)
);

/* 广告 */
create table yjw_advertisement(
	ad_id bigint primary key auto_increment,
	ad_title varchar(255),
	ad_type int,
	ad_image varchar(255),
	ad_content longtext,
	ad_url varchar(255),
	ad_position int,
	ad_memo varchar(255),
	ad_order int,
	ad_create_time datetime,
	ad_modify_time datetime
);

/* 导航 */
create table yjw_navigation(
	nav_id bigint primary key auto_increment,
	nav_name varchar(255),
	nav_position int,
	nav_url varchar(255),
	nav_memo varchar(255),
	nav_order int,
	nav_is_blank bit,
	nav_create_time datetime,
	nav_modify_time datetime
);





































