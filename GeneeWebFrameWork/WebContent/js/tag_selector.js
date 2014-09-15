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
		this.opt.item = this.opt.item || '<tr class="tag_item"><td width="100%"><div class="text"/></td><td><div class="number"/></td><td ><div class="flag"/></td></tr>';
		
		
		
		this._init();

		$(document).bind('click', this._remove_all_menus);

		this._menu_offset = -8;
		if (navigator.appName == "Microsoft Internet Explorer") {
			var browser_version=navigator.appVersion;
			var version=browser_version.split(";");
			var trim_version=version[1].replace(/[ ]/g,""); 
			
			if (trim_Version=="MSIE6.0" || trim_Version=="MSIE7.0") {
				this._menu_offset = -16;
			}
		}

		

		this._bind_event();

		this._render();
	}
	
	TagSelector.prototype = {
			constructor : TagSelector,
			_init : function () {
				this.$root.addClass("tag_selector").addClass("text_like");
				var nowrap = $("<div/>").addClass("nowrap");
				var childRoot = $("<div/>").addClass("tag_selector_link").addClass("tag_selector_first")
					.append($("<a/>").addClass("tag_link").addClass("prevent_default")
					.attr("href", "#").html("全部"));
				var childMore = $("<div/>").addClass("tag_selector_link")
				.addClass("tag_selector_more").addClass("tooltip:请点击")
				.addClass("tooltip_position:left");
				
				
				nowrap.append(childRoot).append(childMore);
				
				this.$root.append(nowrap);
			},
			_change_value: function (id, name) {
				this.group_id = id;
				this.opt.receiver.id.val(id);
				this.opt.receiver.name.val(name);
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
				t.$root.find('.tag_selector_more').click(function(){
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

				t.$root.find('.tag_selector_link:not(.tag_selector_more) a').click(function(e){
					if ($(this).attr('q-tag-id') != undefined) {
						t._change_value($(this).attr('q-tag-id'), $(this).html());
						t.temp_group_id = $(this).attr('q-tag-id');
					} else {
						t.group_id = t.opt.root_id;
						t.temp_group_id = t.opt.root_id;
						t.opt.receiver.id.val("");
						t.opt.receiver.name.val("");
					}
					
					if($(this).parent().nextAll().length > 0) {
						$(this).parent().nextAll().remove();
						$(this).parent().after($("<div/>").addClass("tag_selector_link")
						.addClass("tag_selector_more").addClass("tooltip:请点击")
						.addClass("tooltip_position:left"));
					}
					
					if ($(this).html() == "...") {
						var changeElem = $("<div/>").addClass("tag_selector_link")
						.append($("<a/>").addClass("tag_link").addClass("prevent_default")
								.attr("q-tag-id", $(this).attr('q-tag-id')).attr("href", "#").html($(this).attr('title')));

						$(this).parent().replaceWith(changeElem);

						changeElem.click(function(e){
							if ($(this).attr('q-tag-id') != undefined) {
								_change_value($(this).attr('q-tag-id'), $(this).html());
							} else {
								t.group_id = t.opt.root_id;
								t.opt.receiver.id.val("");
								t.opt.receiver.name.val("");
							}
							
							if($(this).parent().nextAll().length > 0) {
								$(this).parent().nextAll().remove();
								$(this).parent().after($("<div/>").addClass("tag_selector_link")
								.addClass("tag_selector_more").addClass("tooltip:请点击")
								.addClass("tooltip_position:left"));
							}
							
							t.$root.find('.tag_selector_more').click(function(){
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
					}
					
					t._select_tag(t.group_id);
					
					t.$root.find('.tag_selector_more').click(function(){
						if (removeTimeout) {
							clearTimeout(removeTimeout);
							removeTimeout = null;
						}

						if ($current_root != t.$root) {
							t._remove_all_menus();
							$current_root = t.$root;
						}
						
						t._show_next_for_tag(t.group_id);
					})
					.mouseleave(function(){
						if (removeTimeout) {
							clearTimeout(removeTimeout);
						}
						removeTimeout = setTimeout(t._remove_all_menus, 1000);
					});
					
					e.preventDefault();
					return false;
				});
			},
			_render: function() {
				var $links = this.$root.find('.tag_selector_link:not(.tag_selector_first, .tag_selector_more)');
				$links.each(function(i) {
					var $l = $(this);

					if ($l.outerWidth() > 80 && i != $links.length -1) {
						$l.addClass("tag_selector_placeholder").find('a').attr('title', $l.find('a').text()).attr('q-tag-id', $l.find('a').attr('q-tag-id')).html("...");
					}
				});
			},
			_select_tag: function(tag_id) {
				if (this.opt.ajax) {
					this._render();
				}
				else {
					// 设置隐藏提交元素 并自动提交root所在表单
					var $hidden = $('<input type="hidden" />');
					$hidden.attr('name', this.opt.name);
					$hidden.val(tag_id);
					this.$root.parent().append($hidden).submit();
				}
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

						$.each(items, function(i, item) {
							count ++;
							var $t = $(t.opt.item);
							$t.find('.text').html(item.name);
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
							$t.mouseover(function(){
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
								t.group_id = t.temp_group_id;
								$(this).removeClass('tag_item_active');
							})
							.click(function(){
								var $t = $(this);
								//把当前$menu加入队列 才能选择
								$tag_menus[tag_id] = $menu;

								t._fill_content($t);

								t.temp_group_id = item.id;
								t._change_value(item.id, item.name);
								t._select_tag($t.data('tag_id'));
								t._bind_event();
							});
						});

						if (count > 0) {
							$tag_menus[tag_id] = $menu;
							
							$menu
							.mouseenter(function(){
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
			_fill_content: function ($t) {
				var nowrap = this.$root.find(".nowrap").clone();
				this.$root.empty();
				this._get_tab(nowrap, $t);
				this.$root.append(nowrap);
			},
			_get_tab: function (nowrap, $t) {
				if ($tag_map[$t.data("p-tag-id")] != undefined) {
					this._get_tab(nowrap, $tag_map[$t.data("p-tag-id")]);
				}
				
				nowrap.find(".tag_selector_more").remove();
				
				var childElem = $("<div/>").addClass("tag_selector_link").append($("<a/>")
						.addClass("tag_link").addClass("prevent_default").attr("q-tag-id", $t.data('tag_id'))
						.attr("href", "#").html($t.find('.text').html()));
				
				if ($t.data('children_count') == 0) {
					childElem.addClass("tag_selector_last");
				}
				
				nowrap.append(childElem);
				
				
				if ($t.data('children_count') > 0) {
					var childMore = $("<div/>").addClass("tag_selector_link")
							.addClass("tag_selector_more").addClass("tooltip:请点击")
							.addClass("tooltip_position:left");
					nowrap.append(childMore);
				}
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
