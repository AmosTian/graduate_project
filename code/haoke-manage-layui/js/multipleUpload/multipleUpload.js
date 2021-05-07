/*
 * multipleUpload v1.0
 * author JerryZst
 * qq 1309579432
 * Date: 2020/12/8 0007
 */
layui.define(["upload", "jquery", "layer"], function(a) {
    function n(a) {
        this.name = "multipleUploadError",
            this.message = a,
            this.stack = (new Error).stack
    }
    var o, p,$=layui.jquery, b = layui.jquery, c = layui.layer, d = layui.upload, e = "multipleUpload", f = "dropdown-open", g = "multipleUpload-dropdown-menu", h = "multipleUpload-dropdown-menu-nav", i = "multipleUpload-dropdown-hover", j = "fixed", k = "layui-anim layui-anim-upbit", l = "layui-anim layui-anim-fadein", m = [{
        name: "移除",
        icon: "layui-icon-delete",
        type: "multipleUpload_btn_default_1"
    }, {
        name: "查看",
        icon: "layui-icon-picture",
        type: "multipleUpload_btn_default_2"
    }];
    n.prototype = Object.create(Error.prototype),
        n.prototype.constructor = n,
        o = function(a) {
            this.version = "multipleUpload-1.0",
                this.tmpId = (new Date).getTime() + Math.floor(888888 * Math.random()) + 111111,
                this.templateId = "addTemplate_" + this.tmpId,
                this.pictureId = "picture_" + this.tmpId,
            b("#ew-css-multipleUpload").length <= 0 && layui.link(layui.cache.base + "multipleUpload/multipleUpload.css"),
                this.insUpload = null,
                this.uploadData = [],
                this.options = b.extend(!0, {
                    error: a.error || null,
                    data: a.data || [],
                    btn: a.btn || null,
                    hasDefaultBtn: a.hasDefaultBtn || !0,
                    on: a.on || null
                }, a),
                this.init(),
                this.bindEvents()
        },
        o.prototype.init = function() {

            this.uploadData = [];
            for(let i = 0;i < this.options.data.length;++i){
                this.uploadData.push(this.options.data[i])
            }



            var d, e, f, g, h, i, j, k, l, a = this, b = this.options, c = ["elem", "upload_url", "upload_done"];
            for (d = 0; d < c.length; d++)
                if (!b.hasOwnProperty(c[d]) || !b[c[d]])
                    throw new n(c[d] + " is must assign ！");
            if (e = this.getComponents(),
                f = "",
                g = a.btnRender(),
                b.hasOwnProperty("unshift") && b.unshift ? b.data.push({
                    name: "点击上传",
                    url: layui.cache.base + "multipleUpload/images/add_template.png",
                    is_add: 1
                }) : b.data.unshift({
                    name: "点击上传",
                    url: layui.cache.base + "multipleUpload/images/add_template.png",
                    is_add: 1
                }),
                h = '<div class="layui-form layui-row layui-col-space10" id="' + a.tmpId + '">',
                i = "</div>",
            b.data.length > 0)
                for (b.hasOwnProperty("parseData") && b.parseData && (b.data = b.parseData(b.data)),
                         j = 0; j < b.data.length; j++)
                    k = "",
                        k = b.data[j].hasOwnProperty("is_add") && 1 === b.data[j]["is_add"] ? '<div class="layui-col-md2" id="' + this.templateId + '">' + '<div class="project-list-item">' + '<div style="height:120px;position: relative">' + '<img alt="" class="project-list-item-icon" src="' + b.data[j]["url"] + '"/></div>' + '<div class="project-list-item-body">' + '<h2 style="text-align: center;height: 20.8px">' + b.data[j]["name"] + "</h2></div></div></div>" : '<div class="layui-col-md2 ' + this.pictureId + '">' + g + '<div class="project-list-item" data-image-index="' + b.data[j]["index"] + '">' + '<div class="cover_content">' + '<img alt="" class="project-list-item-cover" src="' + b.data[j]["url"] + '"/></div></div></div>',
                        f += k;
            l = h + f + i,
                e.$elem.html(l)
        },
        o.prototype.btnRender = function() {
            var c, a = this.options, b = "";
            if (a.btn && 0 !== a.btn.length || !a.hasDefaultBtn || (a.btn = m),
            a.hasOwnProperty("btn") && a.btn) {
                if (b = ' <div class="multipleUpload-dropdown-menu multipleUpload-dropdown-hover">                <button class="layui-btn layui-btn-normal layui-btn-sm">操作<i class="layui-icon layui-icon-drop"></i>                </button><ul class="multipleUpload-dropdown-menu-nav">',
                a.btn instanceof Array && a.btn.length > 0)
                    for (c = 0; c < a.btn.length; c++)
                        b += '<li class="multipleUpload_btn" data-type="' + (a.btn[c]["type"] || "") + '" data-index="' + c + '"><a><i class="layui-icon ' + (a.btn[c]["icon"] || "layui-icon-template-1") + '"></i>' + a.btn[c]["name"] + "</a></li>";
                else
                    a.btn instanceof Object && (b += '<li class="multipleUpload_btn" data-type="" data-index="-1"><a><i class="layui-icon ' + (a.btn["icon"] || "layui-icon-template-1") + '"></i>' + a.btn["name"] + "</a></li>");
                b = '<div class="edit_content" style="display: none">' + b + "</ul></div></div>"
            }
            return b
        },
        o.prototype.dynamicRender = function(a, c) {
            var d = this.options
                , e = this.btnRender()
                , f = '<div class="layui-col-md2 ' + this.pictureId + '">' + e + '<div class="project-list-item" data-image-index="' + c + '">' + '<div class="cover_content">' + '<img class="project-list-item-cover" src="' + a + '"/></div></div></div>';
            d.hasOwnProperty("unshift") && d.unshift ? b(f).insertBefore("#" + this.templateId) : b(f).insertAfter("#" + this.templateId)
        },
        o.prototype.bindEvents = function() {
            var a = this
                , j = this.options
                , k = this.getComponents()
                , l = function(a) {
                var c = b(a)
                    , d = {
                    elem: c,
                    index: c.attr("data-index")
                };
                return b.extend(d, a)
            }
                , m = "." + g + "." + i;
            b(document).off("mouseenter.dropdown").on("mouseenter.dropdown", m, function() {
                a.show(k.$elem.find("." + h))
            }),
                b(document).off("mouseleave.dropdown").on("mouseleave.dropdown", m, function() {
                    k.$elem.find("." + h).parent("." + g).removeClass(f)
                }),
                b(document).off("mouseenter." + a.pictureId).on("mouseenter." + a.pictureId, "." + a.pictureId, function() {
                    var a = b(this).find(".edit_content");
                    "none" === a.css("display") && a.show()
                }),
                b(document).off("mouseleave." + a.pictureId).on("mouseleave." + a.pictureId, "." + a.pictureId, function() {
                    b(this).find(".edit_content").hide()
                }),
                b(document).off("click").on("click", ".multipleUpload_btn", function() {
                    var h, i, m, n,
                        d = b(this).attr("data-type"),
                        f = b(this).attr("data-index"),
                        g = b(this).parents("." + a.pictureId).find(".project-list-item").attr("data-image-index");
                    if ("multipleUpload_btn_default_1" === d){
                        for (b(this).parents("." + a.pictureId).remove(),
                                 h = 0; h < a.uploadData.length; h++)
                            a.uploadData[h]["index"] == g && a.uploadData.splice(h, 1);
                        console.log(a.uploadData)
                    }
                    else if ("multipleUpload_btn_default_2" === d)
                        i = 0,
                            m = b(this).parents("." + a.pictureId).find(".project-list-item-cover").attr("src"),
                            n = a.uploadData.map(function(a, b) {
                                return m === a.url && (i = b),
                                    {
                                        alt: a.name || "",
                                        src: a.url
                                    }
                            }),
                            c.photos({
                                photos: {
                                    data: n,
                                    start: i
                                },
                                shade: .1,
                                closeBtn: !0
                            });
                    else if (j.btn instanceof Array) {
                        if (j.btn[f].hasOwnProperty("on") && "function" == typeof j.btn[f].on)
                            return j.btn[f].on(b(this));
                        if (j.on)
                            return j.on(b(this), f);
                        layui.event.call(this, e, "btnClick(" + k.filter + ")", l(this))
                    } else if (j.btn instanceof Object) {
                        if (j.btn.hasOwnProperty("on") && "function" == typeof j.btn.on)
                            return j.btn.on(b(this));
                        if (j.on)
                            return j.on(b(this));
                        layui.event.call(this, e, "btnClick(" + k.filter + ")", l(this))
                    }
                }),
                a.insUpload = d.render({
                    elem: "#" + this.templateId,
                    url: j.upload_url,
                    auto: !0,
                    accept: "images",
                    exts: "jpg|png|jpeg",
                    multiple: !0,
                    choose: function(a) {
                        j.upload_choose && j.upload_choose(a)
                    },
                    before: function(a) {
                        c.load(2),
                        j.upload_before && j.upload_before(a)
                    },
                    done: function(b, d) {
                        c.closeAll("loading"),
                            j.upload_done(b, d, function(b) {
                                a.dynamicRender(b, d),
                                    a.uploadData.push({
                                        index: d,
                                        url: b
                                    })
                            })

                        console.log(a.uploadData)
                        //添加后达到4张 不能再新增
                        if(a.uploadData.length == 4){
                            $("div[id^='addTemplate_']").remove();
                        }
                    },
                    error: function(a, b) {
                        return c.closeAll("loading"),
                            c.msg("系统异常，上传图片失败！", {
                                icon: 2
                            }),
                        j.upload_error && j.upload_error(a, b),
                            !1
                    }
                })
        }
        ,
        o.prototype.getComponents = function() {
            var a = this
                , c = b(a.options.elem)
                , d = c.attr("lay-filter");
            return d || (d = a.options.elem.substring(1),
                c.attr("lay-filter", d)),
                {
                    $elem: c,
                    templetHtml: b(a.options.templet).html(),
                    filter: d
                }
        }
        ,
        o.prototype.show = function(a) {
            if (a && a.length > 0) {
                a.hasClass("dropdown-popconfirm") ? (a.removeClass(k),
                    a.addClass(l)) : (a.removeClass(l),
                    a.addClass(k));
                var b;
                return a.addClass("dropdown-bottom-left"),
                    b = "bottom-left",
                    this.forCenter(a, b),
                    a.parent("." + g).addClass(f),
                    b
            }
            return !1
        }
        ,
        o.prototype.forCenter = function(a, b) {
            var c, d, e, f, g, h, i;
            a.hasClass(j) || (c = a.parent().outerWidth(),
                d = a.parent().outerHeight(),
                e = a.outerWidth(),
                f = a.outerHeight(),
                g = b.split("-"),
                h = g[0],
                i = g[1],
            "top" !== h && "bottom" !== h || "center" !== i || a.css("left", (c - e) / 2),
            "left" !== h && "right" !== h || "center" !== i || a.css("top", (d - f) / 2))
        }
        ,
        o.prototype.getUploadImages = function() {
            return this.uploadData
        }
        ,
        o.prototype.getUploadInstance = function() {
            return this.insUpload
        }
        ,
        p = {
            render: function(a) {
                return new o(a)
            }
        },
        a(e, p)
});
