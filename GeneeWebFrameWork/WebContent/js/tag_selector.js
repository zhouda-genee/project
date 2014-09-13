(function($) {

	var $tag_menus = {};
	var $current_root;
	var removeTimeout = null;
	var ajaxTimeout = null;
	var $tag_map = {};


	$.fn.tagSelector = function(opt) {
		var $root = $(this);
		var temp_group_id = opt.root_id;
		var group_id = opt.root_id;
		
		opt = opt || {};
		opt.menu = opt.menu || '<div class="tag_selector_menu"><table class="content" width="1"></table></div>';
		opt.item = opt.item || '<tr class="tag_item"><td width="100%"><div class="text"/></td><td><div class="number"/></td><td ><div class="flag"/></td></tr>';
		
		var _init = function () {
			$root.addClass("tag_selector").addClass("text_like");
			var nowrap = $("<div/>").addClass("nowrap");
			var childRoot = $("<div/>").addClass("tag_selector_link").addClass("tag_selector_first")
				.append($("<a/>").addClass("tag_link").addClass("prevent_default")
				.attr("href", "#").html("全部"));
			var childMore = $("<div/>").addClass("tag_selector_link")
			.addClass("tag_selector_more").addClass("tooltip:请点击")
			.addClass("tooltip_position:left");
			
			
			nowrap.append(childRoot).append(childMore);
			
			$root.append(nowrap);
		};
		
		var _change_value = function (id, name) {
			group_id = id;
			opt.receiver.id.val(id);
			opt.receiver.name.val(name);
		}

		var _zIndex = function($el) {
			var z = $el.css('z-index');
			if (isNaN(z)) z = 0;
			$el.parents().each(function(){
				var myZ = parseInt($(this).css('z-index'));
				if (isNaN(myZ)) myZ = 0;
				z = Math.max(z, myZ);
			});
			return z;
		};

		var _remove_all_menus = function(){
			for (var i in $tag_menus) {
				$tag_menus[i].remove();
			}
		};
		
		var _bind_event = function () {
			$root.find('.tag_selector_more').click(function(){
				if (removeTimeout) {
					clearTimeout(removeTimeout);
					removeTimeout = null;
				}

				if ($current_root != $root) {
					_remove_all_menus();
					$current_root = $root;
				}

				_show_next_for_tag(group_id);
			})
			.mouseleave(function(){
				if (removeTimeout) {
					clearTimeout(removeTimeout);
				}
				removeTimeout = setTimeout(_remove_all_menus, 1000);
			});

			$root.find('.tag_selector_link:not(.tag_selector_more) a').click(function(e){
				if ($(this).attr('q-tag-id') != undefined) {
					_change_value($(this).attr('q-tag-id'), $(this).html());
				} else {
					group_id = opt.root_id
					opt.receiver.id.val("");
					opt.receiver.name.val("");
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
					$(this).parent().after(changeElem);
					$(this).parent().remove();
				}
				
				_select_tag(group_id);
				
				$root.find('.tag_selector_more').click(function(){
					if (removeTimeout) {
						clearTimeout(removeTimeout);
						removeTimeout = null;
					}

					if ($current_root != $root) {
						_remove_all_menus();
						$current_root = $root;
					}

					_show_next_for_tag(group_id);
				})
				.mouseleave(function(){
					if (removeTimeout) {
						clearTimeout(removeTimeout);
					}
					removeTimeout = setTimeout(_remove_all_menus, 1000);
				});
				
				e.preventDefault();
				return false;
			});
		};

		var _render = function() {
			var $div = $root.parent();
			var max_width = Math.min(280, $div.innerWidth());
			if ($root.outerWidth() <= max_width) return;
			
			var $links = $root.find('.tag_selector_link:not(.tag_selector_first, .tag_selector_last, .tag_selector_more)');

			var $placeholder = $('<div class="tag_selector_link tag_selector_placeholder"><a href="#">...</a></div>');
			var w = $root.outerWidth() + $placeholder.outerWidth();
			var $ghost;

			$links.each(function(i) {
				var $l = $(this);
				if (w - $l.outerWidth() <= max_width) {
					$placeholder.find('a').attr('title', $l.find('a').text()).attr('q-tag-id', $l.find('a').attr('q-tag-id'));
					$l.after($placeholder);
					$l.remove();
					return false;
				}
				w -= $l.outerWidth();
				$l.remove();
			});

		};

		var _select_tag = function(tag_id) {
			if (opt.ajax) {
				_render();
			}
			else {
				// 设置隐藏提交元素 并自动提交root所在表单
				var $hidden = $('<input type="hidden" />');
				$hidden.attr('name', opt.name);
				$hidden.val(tag_id);
				$root.parent().append($hidden).submit();
			}
		};
		
		_init();

		$(document).bind('click', _remove_all_menus);

		var _menu_offset = -8;
		if (navigator.appName == "Microsoft Internet Explorer") {
			var browser_version=navigator.appVersion;
			var version=browser_version.split(";");
			var trim_version=version[1].replace(/[ ]/g,""); 
			
			if (trim_Version=="MSIE6.0" || trim_Version=="MSIE7.0") {
				_menu_offset = -16;
			}
		}

		var _show_next_for_tag = function(tag_id, parent_tag_id) {

			var $parent_item;
			var $parent_menu;
			
			
			var _fill_content = function ($t) {
				var nowrap = $root.find(".nowrap").clone();
				$root.empty();
				_get_tab(nowrap, $t);
				$root.append(nowrap);
			}
			
			var _get_tab = function (nowrap, $t) {
				if ($tag_map[$t.data("p-tag-id")] != undefined) {
					_get_tab(nowrap, $tag_map[$t.data("p-tag-id")]);
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
			};
			
			

			var _remove = function($m){
				$m.find('.tag_item').each(function(){
					
					var $item = $(this);
					var id = $item.data('tag_id');

					if (id) {
						var $el = $tag_menus[id];
						if ($el) {
							_remove($el);
							$el.remove();
							delete $tag_menus[id];
						}
					}
				})
			};

			if (parent_tag_id != undefined) {
				$parent_menu = $tag_menus[parent_tag_id];
				if ($parent_menu) {
					_remove($parent_menu);

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

			var $menu = $(opt.menu);
			var $menu_content = $menu.find('.content');
			$menu.append('<div class="loading">&#160;</div>');
			$menu.appendTo('body');

			if ($parent_menu) {
				var ioffset = $parent_item.offset();
				$menu.css({zIndex: $parent_menu.css('z-index') + 1, left: ioffset.left + $parent_item.width() + _menu_offset, top: ioffset.top });
			}
			else {
				$parent_item = $root.find('.tag_selector_more');
				var ioffset = $parent_item.offset();
				$menu.css({zIndex: _zIndex($root) + 1, left: ioffset.left + $parent_item.width(), top: ioffset.top});
			}
			
			var parameter = {
				id: group_id
			};
			$.get(opt.url, parameter, function (data) {
				if (data.hasOwnProperty('result')) {
					var items = data.result || {};
					var count = 0;
					$menu.find('.loading').remove();

					$.each(items, function(i, item) {
						count ++;
						var $t = $(opt.item);
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
						$t.mouseenter(function(){
							var $t = $(this);
							$t.addClass('tag_item_active');
							if (ajaxTimeout) {
								clearTimeout(ajaxTimeout);
								ajaxTimeout = null;
							}
							
							group_id = item.id;
							
							if ($t.data('children_count') > 0) {
								ajaxTimeout = setTimeout(function(){
									_show_next_for_tag($t.data('tag_id'), tag_id);
								}, 50);
							}
							else {
								//如果没有后代元素，把其他的tag的后代移出
								_remove($menu);		
							}
							
						})
						.mouseleave(function(){
							group_id = temp_group_id;
							$(this).removeClass('tag_item_active');
						})
						.click(function(){
							var $t = $(this);
							//把当前$menu加入队列 才能选择
							$tag_menus[tag_id] = $menu;

							_fill_content($t);

							temp_group_id = item.id;
							_change_value(item.id, item.name);
							_select_tag($t.data('tag_id'));
							_bind_event();
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
							$root.data('removeTimeout', null);
						})
						.mouseleave(function(){
							if (removeTimeout) {
								clearTimeout(removeTimeout);
							}
							removeTimeout = setTimeout(_remove_all_menus, 1000);
						});
					}

					delete data.result;
				}
				
				if ($tag_menus[tag_id]!=$menu) {
					$menu.remove();
				}
	        }, "json");
			
		};

		_bind_event();

		_render();

	};

})($);
