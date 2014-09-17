(function($) {

	var $tag_menus = {};
	var $current_root;
	var removeTimeout = null;
	var ajaxTimeout = null;
	var $tag_map = {};
	
	var TagSelector = function (element, option) {
		this.$root = $(element);
		
		this.opt = option || {};
		
		this.temp_group_id = this.opt.root_id;
		this.group_id = this.opt.root_id;
		
		this.opt.menu = this.opt.menu || '<div class="tag_selector_menu"><table class="content" width="1"></table></div>';
		this.opt.item = this.opt.item || '<tr class="tag_item"><td><div class="ckb"/></td><td width="100%"><div class="text"/></td><td><div class="number"/></td><td ><div class="flag"/></td></tr>';
		
		this._init();

		this._menu_offset = -8;
		if (navigator.appName == "Microsoft Internet Explorer") {
			var browser_version=navigator.appVersion;
			var version=browser_version.split(";");
			var trim_version=version[1].replace(/[ ]/g,""); 
			
			if (trim_Version=="MSIE6.0" || trim_Version=="MSIE7.0") {
				this._menu_offset = -16;
			}
		}
		
		var t = this;
		$(document).bind("click", function(){
			t._remove_all_menus();
		});

		this._bind_event();
	}
	
	TagSelector.prototype = {
			constructor : TagSelector,
			_init : function () {
				this.$root.addClass("tag_selector").addClass("text_like");
				var nowrap = $("<div/>").addClass("nowrap");
				var childRoot = $("<div/>").addClass("tag_selector_link").addClass("tag_selector_first")
					.append($("<a/>").addClass("tag_link").addClass("prevent_default")
					.attr("href", "#").html("选择"));
				var childMore = $("<div/>").addClass("tag_selector_link")
				.addClass("tag_selector_more").addClass("tooltip:请点击")
				.addClass("tooltip_position:left");
				
				nowrap.append(childRoot).append(childMore);
				
				this.$root.append(nowrap);
			},
			_change_value: function (id, name) {
				var idStr = "";
				var nameStr = "";
				var htmlStr = "";
				
				$(document).find("input[name^='menu_check_box_'][type='checkbox']:checked").each(function(){
					idStr += $(this).val() + ",";
					nameStr += $(this).parent().parent().parent().find(".text").html() + ",";
					htmlStr += $(this).parent().parent().parent().find(".text").html() + ",";
				});
				
				idStr = idStr.substring(0, idStr.length - 1);
				nameStr = nameStr.substring(0, nameStr.length - 1);
				htmlStr = htmlStr.substring(0, htmlStr.length - 1);
				
				if (htmlStr.length > 20) {
					htmlStr = htmlStr.substring(0, 20) + "...";
				}
				
				this.opt.receiver.id.val(idStr);
				this.opt.receiver.name.val(nameStr);

				this.$root.find(".tag_selector_link:not(:first):not(.tag_selector_more)").remove();
				if ($(document).find("input[name^='menu_check_box_'][type='checkbox']:checked").length > 0) {
					this.$root.find(".nowrap").append($("<div/>").addClass("tag_selector_link")
							.append($("<a/>").addClass("tag_link").addClass("prevent_default").html(htmlStr)));
				}
			},
			_zIndex: function($el) {
				var z = $el.css('z-index');
				if (isNaN(z)) z = 0;
				$el.parents().each(function(){
					var myZ = parseInt($(this).css('z-index'));
					if (isNaN(myZ)) myZ = 0;
					z = Math.max(z, myZ);
				});
				return z;
			},
			_remove_all_menus: function(){
				for (var i in $tag_menus) {
					$tag_menus[i].remove();
				}
			},
			_bind_event: function () {
				var t = this;
				t.$root.find('.tag_selector_link:not(.tag_selector_more) a').click(function(e){
					$(this).parent().nextAll().remove();
					var childMore = $("<div/>").addClass("tag_selector_link")
					.addClass("tag_selector_more").addClass("tooltip:请点击")
					.addClass("tooltip_position:left");
					
					$(this).parent().after(childMore);
					
					t.opt.receiver.id.val("");
					t.opt.receiver.name.val("");
					
					t.$root.find('.tag_selector_more').click(function(e){
						if (removeTimeout) {
							clearTimeout(removeTimeout);
							removeTimeout = null;
						}

						if ($current_root != t.$root) {
							t._remove_all_menus();
							$current_root = t.$root;
						}

						ajaxTimeout = setTimeout(function(){
							t._show_next_for_tag(t.group_id);
						}, 50);
					});
					
					e.preventDefault();
					return false;
				});
				
				t.$root.find('.tag_selector_more').click(function(e){
					if (removeTimeout) {
						clearTimeout(removeTimeout);
						removeTimeout = null;
					}

					if ($current_root != t.$root) {
						t._remove_all_menus();
						$current_root = t.$root;
					}

					ajaxTimeout = setTimeout(function(){
						t._show_next_for_tag(t.group_id);
					}, 50);
					
					e.preventDefault();
					return false;
				});
			},
			_show_next_for_tag: function(tag_id, parent_tag_id) {

				var t = this;
				var $parent_item;
				var $parent_menu;

				if (parent_tag_id != undefined) {
					$parent_menu = $tag_menus[parent_tag_id];
					if ($parent_menu) {
						this._remove($parent_menu);

						$parent_menu.find('.tag_item').each(function(){
							var $item = $(this);
							var id = $item.data('tag_id');
							if (id == tag_id) {
								$parent_item = $item;
								return false;
							}
						});
					}
				}
				else {
					var $el = $tag_menus[tag_id];
					if ($el) {
						$el.remove();
						delete $tag_menus[tag_id];
					}
				}

				var $menu = $(this.opt.menu);
				var $menu_content = $menu.find('.content');
				$menu.append('<div class="loading">&#160;</div>');
				$menu.appendTo('body');
				
				if ($parent_menu) {
					var ioffset = $parent_item.offset();
					$menu.css({zIndex: $parent_menu.css('z-index') + 1, left: ioffset.left + $parent_item.width() + t._menu_offset, top: ioffset.top });
				}
				else {
					$parent_item = t.$root.find('.tag_selector_more');
					var ioffset = $parent_item.offset();
					$menu.css({zIndex: t._zIndex(t.$root) + 1, left: ioffset.left + $parent_item.width(), top: ioffset.top});
				}
				
				var parameter = {
					id: tag_id
				};
				
				$.get(t.opt.url, parameter, function (data) {
					if (data.hasOwnProperty('result')) {
						var items = data.result || {};
						var count = 0;
						$menu.find('.loading').remove();
						
						var $checkAllItem = $(t.opt.item);
						$checkAllItem.find('.text').html("全选");
						
						var checkboxAll = $("<input/>").attr({
							"type" : "checkbox",
							"name" : "all_menu_check_box_" + tag_id
						});
						
						$checkAllItem.find('.ckb').empty().append(checkboxAll);
						
						checkboxAll.change(function() {
							$(document).find("input:not([name='menu_check_box_" + tag_id + "'],[name='all_menu_check_box_" + tag_id + "'])[type='checkbox']").each(function(){
								$(this).prop('checked', false);
							});
							
							$(document).find("input[name='menu_check_box_" + tag_id + "'][type='checkbox']").each(function(){
								$(this).prop("checked", checkboxAll.prop('checked'));
							});
							
							t._change_value();
						});
						$checkAllItem.mouseenter(function(){
							$(document).unbind("click");
						})
						.mouseleave(function(){
							$(document).bind("click", function(){
								t._remove_all_menus();
							});
						});;
						
						$menu_content.append($checkAllItem);
						
						$.each(items, function(i, item) {
							count ++;
							var $t = $(t.opt.item);
							$t.find('.text').html(item.name);
							
							var checkbox = $("<input/>").attr({
								"type" : "checkbox",
								"name" : "menu_check_box_" + tag_id,
								"value" : item.id
							});
							
							if (t.opt.receiver.id.val() != "") {
								var idArr = t.opt.receiver.id.val().split(',');
								
								for(var i=0, l=idArr.length; i<l; i++){
					                if(idArr[i] == item.id){
					                	   checkbox.prop('checked', true);
					                    break;
					                }   
					            } 
							}
							$t.find('.ckb').empty().append(checkbox);
							
							checkbox.change(function() {
								$(document).find("input:not([name='" + $(this).attr("name") + "'])[type='checkbox']").each(function(){
									$(this).prop('checked', false);
								});
								
								if ($(document).find("input[name='menu_check_box_" + tag_id + "'][type='checkbox']:checked").length 
										== $(document).find("input[name='menu_check_box_" + tag_id + "'][type='checkbox']")) {
									checkboxAll.prop('checked', true);
								} else {
									checkboxAll.prop('checked', false);
								}
								
								t._change_value();
							});
							
							if ($(document).find("input[name='menu_check_box_" + tag_id + "'][type='checkbox']:checked").length 
									== $(document).find("input[name='menu_check_box_" + tag_id + "'][type='checkbox']").length) {
								checkboxAll.prop('checked', true);
							} else {
								checkboxAll.prop('checked', false);
							}
							

							$t.data('tag_id', item.id);

							if (parent_tag_id != undefined) {
								$t.data("p-tag-id", tag_id);
							}
							
							if (item.ccount > 0) {
								$t.find('.number').html(item.ccount);
								$t.find('.flag').addClass('flag_more');
							}
							
							$tag_map[item.id] = $t;
							$menu_content.append($t);
							$t.data('children_count', item.ccount);
							
							$t.mouseenter(function(){
								$(document).unbind("click");
								var $t = $(this);
								$t.addClass('tag_item_active');
								if (ajaxTimeout) {
									clearTimeout(ajaxTimeout);
									ajaxTimeout = null;
								}
								
								this.group_id = item.id;
								
								if ($t.data('children_count') > 0) {
									ajaxTimeout = setTimeout(function(){
										t._show_next_for_tag($t.data('tag_id'), tag_id);
									}, 50);
								}
								else {
									//如果没有后代元素，把其他的tag的后代移出
									t._remove($menu);		
								}
								
							})
							.mouseleave(function(){
								$(document).bind("click", function(){
									t._remove_all_menus();
								});
								t.group_id = t.temp_group_id;
								$(this).removeClass('tag_item_active');
							});
						});

						if (count > 0) {
							$tag_menus[tag_id] = $menu;
							
							$menu
							.mouseover(function(){
								if (removeTimeout) {
									clearTimeout(removeTimeout);
									removeTimeout = null;
								}
								t.$root.data('removeTimeout', null);
							})
							.mouseleave(function(){
								if (removeTimeout) {
									clearTimeout(removeTimeout);
								}
								removeTimeout = setTimeout(t._remove_all_menus, 1000);
							});
						}

						delete data.result;
					}
					
					if ($tag_menus[tag_id]!=$menu) {
						$menu.remove();
					}
		        }, "json");
				
			},
			_remove : function($m){
				var t = this;
				$m.find('.tag_item').each(function(){
					
					var $item = $(this);
					var id = $item.data('tag_id');

					if (id) {
						var $el = $tag_menus[id];
						if ($el) {
							t._remove($el);
							$el.remove();
							delete $tag_menus[id];
						}
					}
				});
			}
	};

	$.fn.tagSelector = function(option) {
		return this
		.each(function() {
			var $this = $(this), data = $this.data('tagSelector'), options = typeof option == 'object'
					&& option;
			if (!data) {
				$this.data('tagSelector',
						(data = new TagSelector(this, $.extend({},
								$.fn.tagSelector.defaults, options))));
			}
			if (typeof option == 'string')
				data[option]();
		});
	};
	
	$.fn.tagSelector.defaults = {};
	$.fn.tagSelector.Constructor = TagSelector;
})($);
