function _classCallCheck(e,t){
    
    if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}var check=function(){var e={checkuname:function(e){var n=0;return null==e||""==e?n=1001:t._getstrlen(e)<6?n=1002:t._getstrlen(e)>14&&(n=1003),n},checkpass:function(e){var t=0;if(null==e||""==e.length)t=1005;else if(e.length<6)t=1006;else if(e.length>20)t=1007;else{var n="^[0-9a-zA-Z_]+$",i=new RegExp(n);i.test(e)||(t=1008)}return t},checkemail:function(e){var t=0,n=/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;return n.test(e)||(t=1009),t},checkpiccode:function(e){var t=0;return(null==e||4!==e.length)&&(t=1010),t},checksignature:function(e){return null!=e&&t._getstrlen(e).length>50?1011:0},_getstrlen:function(e){var t=0,n=0;n=e.length;for(var i=0;n>i;i++){var r=e.charAt(i);t++,escape(r).length>4&&t++}return t}},t=e;return Monitor(e)}(),alertStyle={"#zalert":{display:"none","font-family":"'微软雅黑'",top:"0",left:"0",width:"100%",height:"100%",position:"fixed","z-index":"99999"},"#zalert .bk":{opacity:"0.5",width:"100%",height:"100%",background:"#000",filter:"alpha(opacity=50)"},"#zalert .z-close":{position:"absolute",right:"8px","font-size":"24px",top:"0px",color:"#B3B2B2",cursor:"pointer"},"#zalert .z-close:hover":{color:"#5F5A5A"},"#zalert .panel":{background:"#FFF","border-radius":"3px",position:"fixed","z-index":"99999999",width:"400px","min-height":"100px",padding:"15px 20px",left:"50%",top:"25%","margin-left":"-200px"},"#zalert .con-inf":{margin:"30px auto","text-align":"center","font-size":"14px",height:"24px","line-height":"24px",width:"100%"},"#zalert .zbtn":{width:"70px",color:"#fff","background-color":"#F7BC46","font-size":"16px","text-align":"center",cursor:"pointer",margin:"0px auto 20px",top:"25%",padding:"7px 5px","border-radius":"19px"},"#zalert .zbtn:hover":{"background-color":"#F7BC46"}};window.zalert=function(){var e={init:function(){this.cstyle(),this.initEvent(),this.cpage()},cstyle:function(){$('<style type="text/css">'+e.getstyle(alertStyle)+"</style>").appendTo("head")},cpage:function(){var e=zen("div#zalert>div.bk+div.panel"),t=$(e).find(".panel");t.zen("div.zalert-title-wrap>div.z-title+div.z-close"),t.zen("div.content>div.con-inf+div.zbtn"),$(t).find(".z-close").html("×"),$(t).find(".zbtn").html("确定"),$("body").append(e)},initEvent:function(){$(document).on("click","#zalert .zbtn,#zalert .z-close",function(){$("#zalert").hide()})},alert:function(e,t){$("#zalert").fadeIn(),$("#zalert .con-inf").html(e),null!=t&&$(".z-title").html(t)},getstyle:function(e){function t(e){var t="";for(var n in e)if(/[#\.]/gi.test(n)&&"object"==typeof e[n]){var i=e[n],r="";for(var o in i)r+=o+":"+i[o]+";";t+=n+"{"+r+"}"}return t}var n=t(e);return n}};return $(document).ready(function(){e.init()}),function(t,n){e.alert(t,n)}}();var _createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var i=t[n];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(e,i.key,i)}}return function(t,n,i){return n&&e(t.prototype,n),i&&e(t,i),t}}(),Swiper=function(){function e(t){var n=this;if(_classCallCheck(this,e),this.config=e.mergeSettings(t),this.selectorWrap="string"==typeof this.config.el?document.querySelector(this.config.el):this.config.el,this.selector=this.selectorWrap.querySelector(".swiper-wrapper"),null===this.selector)throw new Error("Something wrong with your selector 😭");this.resolveSlidesNumber(),this.selectorWidth=this.selector.offsetWidth,this.innerElements=[].slice.call(this.selector.children),this.currentSlide=this.config.loop?this.config.startIndex%this.innerElements.length:Math.max(0,Math.min(this.config.startIndex,this.innerElements.length-this.perPage)),this.transformProperty=e.webkitOrNot(),["resizeHandler","touchstartHandler","touchendHandler","touchmoveHandler","mousedownHandler","mouseupHandler","mouseleaveHandler","mousemoveHandler","clickHandler"].forEach(function(e){n[e]=n[e].bind(n)}),this.init()}return _createClass(e,[{key:"attachEvents",value:function(){var e=this;if(window.addEventListener("resize",this.resizeHandler),this.config.draggable){this.pointerDown=!1,this.drag={startX:0,endX:0,startY:0,letItGo:null,preventClick:!1},this.selector.addEventListener("touchstart",this.touchstartHandler),this.selector.addEventListener("touchend",this.touchendHandler),this.selector.addEventListener("touchmove",this.touchmoveHandler),this.selector.addEventListener("mousedown",this.mousedownHandler),this.selector.addEventListener("mouseup",this.mouseupHandler),this.selector.addEventListener("mouseleave",this.mouseleaveHandler),this.selector.addEventListener("mousemove",this.mousemoveHandler),this.selector.addEventListener("click",this.clickHandler);var t=this.selectorWrap.querySelector(this.config.navigation.prevEl);t&&t.addEventListener("click",function(){e.prev()});var n=this.selectorWrap.querySelector(this.config.navigation.nextEl);n&&n.addEventListener("click",function(){e.next()})}}},{key:"detachEvents",value:function(){window.removeEventListener("resize",this.resizeHandler),this.selector.removeEventListener("touchstart",this.touchstartHandler),this.selector.removeEventListener("touchend",this.touchendHandler),this.selector.removeEventListener("touchmove",this.touchmoveHandler),this.selector.removeEventListener("mousedown",this.mousedownHandler),this.selector.removeEventListener("mouseup",this.mouseupHandler),this.selector.removeEventListener("mouseleave",this.mouseleaveHandler),this.selector.removeEventListener("mousemove",this.mousemoveHandler),this.selector.removeEventListener("click",this.clickHandler)}},{key:"init",value:function(){this.attachEvents(),this.selector.style.overflow="hidden",this.selector.style.direction=this.config.rtl?"rtl":"ltr",this.buildSliderFrame(),this.config.onInit.call(this)}},{key:"buildSliderFrame",value:function(){var e=this.selectorWidth/this.perPage,t=this.config.loop?this.innerElements.length+2*this.perPage:this.innerElements.length;this.sliderFrame=document.createElement("div"),this.sliderFrame.style.width=e*t+"px",this.enableTransition(),this.config.draggable&&(this.selector.style.cursor="-webkit-grab");var n=document.createDocumentFragment();if(this.config.loop)for(var i=this.innerElements.length-this.perPage;i<this.innerElements.length;i++){var r=this.buildSliderFrameItem(this.innerElements[i].cloneNode(!0));n.appendChild(r)}for(var i=0;i<this.innerElements.length;i++){var r=this.buildSliderFrameItem(this.innerElements[i]);n.appendChild(r)}if(this.config.loop)for(var i=0;i<this.perPage;i++){var r=this.buildSliderFrameItem(this.innerElements[i].cloneNode(!0));n.appendChild(r)}this.sliderFrame.appendChild(n),this.selector.innerHTML="",this.selector.appendChild(this.sliderFrame),this.slideToCurrent()}},{key:"buildSliderFrameItem",value:function(e){var t=document.createElement("div");return t.style.cssFloat=this.config.rtl?"right":"left",t.style.float=this.config.rtl?"right":"left",t.style.width=(this.config.loop?100/(this.innerElements.length+2*this.perPage):100/this.innerElements.length)+"%",t.appendChild(e),t}},{key:"resolveSlidesNumber",value:function(){if("number"==typeof this.config.perPage)this.perPage=this.config.perPage;else if("object"==typeof this.config.perPage){this.perPage=1;for(var e in this.config.perPage)window.innerWidth>=e&&(this.perPage=this.config.perPage[e])}}},{key:"prev",value:function(e,t){if(void 0===e&&(e=1),!(this.innerElements.length<=this.perPage)){var n=this.currentSlide;if(this.config.loop){var i=this.currentSlide-e<0;if(i){this.disableTransition();var r=this.currentSlide+this.innerElements.length,o=this.perPage,s=r+o,a=(this.config.rtl?1:-1)*s*(this.selectorWidth/this.perPage),c=this.config.draggable?this.drag.endX-this.drag.startX:0;this.sliderFrame.style[this.transformProperty]="translate3d("+(a+c)+"px,0,0)",this.currentSlide=r-e}else this.currentSlide=this.currentSlide-e}else this.currentSlide=Math.max(this.currentSlide-e,0);n!==this.currentSlide&&(this.slideToCurrent(this.config.loop),this.config.onChange.call(this),t&&t.call(this))}}},{key:"next",value:function(e,t){if(void 0===e&&(e=1),!(this.innerElements.length<=this.perPage)){var n=this.currentSlide;if(this.config.loop){var i=this.currentSlide+e>this.innerElements.length-this.perPage;if(i){this.disableTransition();var r=this.currentSlide-this.innerElements.length,o=this.perPage,s=r+o,a=(this.config.rtl?1:-1)*s*(this.selectorWidth/this.perPage),c=this.config.draggable?this.drag.endX-this.drag.startX:0;this.sliderFrame.style[this.transformProperty]="translate3d("+(a+c)+"px,0,0)",this.currentSlide=r+e}else this.currentSlide=this.currentSlide+e}else this.currentSlide=Math.min(this.currentSlide+e,this.innerElements.length-this.perPage);n!==this.currentSlide&&(this.slideToCurrent(this.config.loop),this.config.onChange.call(this),t&&t.call(this))}}},{key:"disableTransition",value:function(){this.sliderFrame.style.webkitTransition="all 0ms "+this.config.easing,this.sliderFrame.style.transition="all 0ms "+this.config.easing}},{key:"enableTransition",value:function(){this.sliderFrame.style.webkitTransition="all "+this.config.duration+"ms "+this.config.easing,this.sliderFrame.style.transition="all "+this.config.duration+"ms "+this.config.easing}},{key:"goTo",value:function(e,t){if(!(this.innerElements.length<=this.perPage)){var n=this.currentSlide;this.currentSlide=this.config.loop?e%this.innerElements.length:Math.min(Math.max(e,0),this.innerElements.length-this.perPage),n!==this.currentSlide&&(this.slideToCurrent(),this.config.onChange.call(this),t&&t.call(this))}}},{key:"slideToCurrent",value:function(e){var t=this,n=this.config.loop?this.currentSlide+this.perPage:this.currentSlide,i=(this.config.rtl?1:-1)*n*(this.selectorWidth/this.perPage);e?requestAnimationFrame(function(){requestAnimationFrame(function(){t.enableTransition(),t.sliderFrame.style[t.transformProperty]="translate3d("+i+"px,0,0)"})}):this.sliderFrame.style[this.transformProperty]="translate3d("+i+"px,0,0)"}},{key:"updateAfterDrag",value:function(){var e=(this.config.rtl?-1:1)*(this.drag.endX-this.drag.startX),t=Math.abs(e),n=this.config.multipleDrag?Math.ceil(t/(this.selectorWidth/this.perPage)):1,i=e>0&&this.currentSlide-n<0,r=0>e&&this.currentSlide+n>this.innerElements.length-this.perPage;e>0&&t>this.config.threshold&&this.innerElements.length>this.perPage?this.prev(n):0>e&&t>this.config.threshold&&this.innerElements.length>this.perPage&&this.next(n),this.slideToCurrent(i||r)}},{key:"resizeHandler",value:function(){this.resolveSlidesNumber(),this.currentSlide+this.perPage>this.innerElements.length&&(this.currentSlide=this.innerElements.length<=this.perPage?0:this.innerElements.length-this.perPage),this.selectorWidth=this.selector.offsetWidth,this.buildSliderFrame()}},{key:"clearDrag",value:function(){this.drag={startX:0,endX:0,startY:0,letItGo:null,preventClick:this.drag.preventClick}}},{key:"touchstartHandler",value:function(e){var t=-1!==["TEXTAREA","OPTION","INPUT","SELECT"].indexOf(e.target.nodeName);t||(e.stopPropagation(),this.pointerDown=!0,this.drag.startX=e.touches[0].pageX,this.drag.startY=e.touches[0].pageY)}},{key:"touchendHandler",value:function(e){e.stopPropagation(),this.pointerDown=!1,this.enableTransition(),this.drag.endX&&this.updateAfterDrag(),this.clearDrag()}},{key:"touchmoveHandler",value:function(e){if(e.stopPropagation(),null===this.drag.letItGo&&(this.drag.letItGo=Math.abs(this.drag.startY-e.touches[0].pageY)<Math.abs(this.drag.startX-e.touches[0].pageX)),this.pointerDown&&this.drag.letItGo){e.preventDefault(),this.drag.endX=e.touches[0].pageX,this.sliderFrame.style.webkitTransition="all 0ms "+this.config.easing,this.sliderFrame.style.transition="all 0ms "+this.config.easing;var t=this.config.loop?this.currentSlide+this.perPage:this.currentSlide,n=t*(this.selectorWidth/this.perPage),i=this.drag.endX-this.drag.startX,r=this.config.rtl?n+i:n-i;this.sliderFrame.style[this.transformProperty]="translate3d("+(this.config.rtl?1:-1)*r+"px,0,0)"}}},{key:"mousedownHandler",value:function(e){var t=-1!==["TEXTAREA","OPTION","INPUT","SELECT"].indexOf(e.target.nodeName);t||(e.preventDefault(),e.stopPropagation(),this.pointerDown=!0,this.drag.startX=e.pageX)}},{key:"mouseupHandler",value:function(e){e.stopPropagation(),this.pointerDown=!1,this.selector.style.cursor="-webkit-grab",this.enableTransition(),this.drag.endX&&this.updateAfterDrag(),this.clearDrag()}},{key:"mousemoveHandler",value:function(e){if(e.preventDefault(),this.pointerDown){"A"===e.target.nodeName&&(this.drag.preventClick=!0),this.drag.endX=e.pageX,this.selector.style.cursor="-webkit-grabbing",this.sliderFrame.style.webkitTransition="all 0ms "+this.config.easing,this.sliderFrame.style.transition="all 0ms "+this.config.easing;var t=this.config.loop?this.currentSlide+this.perPage:this.currentSlide,n=t*(this.selectorWidth/this.perPage),i=this.drag.endX-this.drag.startX,r=this.config.rtl?n+i:n-i;this.sliderFrame.style[this.transformProperty]="translate3d("+(this.config.rtl?1:-1)*r+"px,0,0)"}}},{key:"mouseleaveHandler",value:function(e){this.pointerDown&&(this.pointerDown=!1,this.selector.style.cursor="-webkit-grab",this.drag.endX=e.pageX,this.drag.preventClick=!1,this.enableTransition(),this.updateAfterDrag(),this.clearDrag())}},{key:"clickHandler",value:function(e){this.drag.preventClick&&e.preventDefault(),this.drag.preventClick=!1}},{key:"remove",value:function(e,t){if(0>e||e>=this.innerElements.length)throw new Error("Item to remove doesn't exist 😭");var n=e<this.currentSlide,i=this.currentSlide+this.perPage-1===e;(n||i)&&this.currentSlide--,this.innerElements.splice(e,1),this.buildSliderFrame(),t&&t.call(this)}},{key:"insert",value:function(e,t,n){if(0>t||t>this.innerElements.length+1)throw new Error("Unable to inset it at this index 😭");if(-1!==this.innerElements.indexOf(e))throw new Error("The same item in a carousel? Really? Nope 😭");var i=t<=this.currentSlide>0&&this.innerElements.length;this.currentSlide=i?this.currentSlide+1:this.currentSlide,this.innerElements.splice(t,0,e),this.buildSliderFrame(),n&&n.call(this)}},{key:"prepend",value:function(e,t){this.insert(e,0),t&&t.call(this)}},{key:"append",value:function(e,t){this.insert(e,this.innerElements.length+1),t&&t.call(this)}},{key:"destroy",value:function(e,t){if(void 0===e&&(e=!1),this.detachEvents(),this.selector.style.cursor="auto",e){for(var n=document.createDocumentFragment(),i=0;i<this.innerElements.length;i++)n.appendChild(this.innerElements[i]);this.selector.innerHTML="",this.selector.appendChild(n),this.selector.removeAttribute("style")}t&&t.call(this)}}],[{key:"mergeSettings",value:function(e){var t={el:".swiper-container",duration:200,easing:"ease-out",perPage:1,startIndex:0,draggable:!1,multipleDrag:!0,threshold:20,loop:!0,rtl:!1,navigation:{nextEl:".next",prevEl:".prev"},onInit:function(){},onChange:function(){}},n=e;for(var i in n)t[i]=n[i];return t}},{key:"webkitOrNot",value:function(){var e=document.documentElement.style;return"string"==typeof e.transform?"transform":"WebkitTransform"}}]),e}();!function(e,t,n){function i(n,i){this.wrapper="string"==typeof n?t.querySelector(n):n,this.scroller=this.wrapper.children[0],this.scrollerStyle=this.scroller.style,this.options={resizeScrollbars:!0,mouseWheelSpeed:20,snapThreshold:.334,disablePointer:!a.hasPointer,disableTouch:a.hasPointer||!a.hasTouch,disableMouse:a.hasPointer||a.hasTouch,startX:0,startY:0,scrollY:!0,directionLockThreshold:5,momentum:!0,bounce:!0,bounceTime:600,bounceEasing:"",preventDefault:!0,preventDefaultException:{tagName:/^(INPUT|TEXTAREA|BUTTON|SELECT)$/},HWCompositing:!0,useTransition:!0,useTransform:!0,bindToWrapper:"undefined"==typeof e.onmousedown};for(var r in i)this.options[r]=i[r];this.translateZ=this.options.HWCompositing&&a.hasPerspective?" translateZ(0)":"",this.options.useTransition=a.hasTransition&&this.options.useTransition,this.options.useTransform=a.hasTransform&&this.options.useTransform,this.options.eventPassthrough=this.options.eventPassthrough===!0?"vertical":this.options.eventPassthrough,this.options.preventDefault=!this.options.eventPassthrough&&this.options.preventDefault,this.options.scrollY="vertical"==this.options.eventPassthrough?!1:this.options.scrollY,this.options.scrollX="horizontal"==this.options.eventPassthrough?!1:this.options.scrollX,this.options.freeScroll=this.options.freeScroll&&!this.options.eventPassthrough,this.options.directionLockThreshold=this.options.eventPassthrough?0:this.options.directionLockThreshold,this.options.bounceEasing="string"==typeof this.options.bounceEasing?a.ease[this.options.bounceEasing]||a.ease.circular:this.options.bounceEasing,this.options.resizePolling=void 0===this.options.resizePolling?60:this.options.resizePolling,this.options.tap===!0&&(this.options.tap="tap"),this.options.useTransition||this.options.useTransform||/relative|absolute/i.test(this.scrollerStyle.position)||(this.scrollerStyle.position="relative"),"scale"==this.options.shrinkScrollbars&&(this.options.useTransition=!1),this.options.invertWheelDirection=this.options.invertWheelDirection?-1:1,this.x=0,this.y=0,this.directionX=0,this.directionY=0,this._events={},this._init(),this.refresh(),this.scrollTo(this.options.startX,this.options.startY),this.enable()}function r(e,n,i){var r=t.createElement("div"),o=t.createElement("div");return i===!0&&(r.style.cssText="position:absolute;z-index:9999",o.style.cssText="-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;position:absolute;background:rgba(0,0,0,0.5);border:1px solid rgba(255,255,255,0.9);border-radius:3px"),o.className="iScrollIndicator","h"==e?(i===!0&&(r.style.cssText+=";height:7px;left:2px;right:2px;bottom:0",o.style.height="100%"),r.className="iScrollHorizontalScrollbar"):(i===!0&&(r.style.cssText+=";width:7px;bottom:2px;top:2px;right:1px",o.style.width="100%"),r.className="iScrollVerticalScrollbar"),r.style.cssText+=";overflow:hidden",n||(r.style.pointerEvents="none"),r.appendChild(o),r}function o(n,i){this.wrapper="string"==typeof i.el?t.querySelector(i.el):i.el,this.wrapperStyle=this.wrapper.style,this.indicator=this.wrapper.children[0],this.indicatorStyle=this.indicator.style,this.scroller=n,this.options={listenX:!0,listenY:!0,interactive:!1,resize:!0,defaultScrollbars:!1,shrink:!1,fade:!1,speedRatioX:0,speedRatioY:0};for(var r in i)this.options[r]=i[r];if(this.sizeRatioX=1,this.sizeRatioY=1,this.maxPosX=0,this.maxPosY=0,this.options.interactive&&(this.options.disableTouch||(a.addEvent(this.indicator,"touchstart",this),a.addEvent(e,"touchend",this)),this.options.disablePointer||(a.addEvent(this.indicator,a.prefixPointerEvent("pointerdown"),this),a.addEvent(e,a.prefixPointerEvent("pointerup"),this)),this.options.disableMouse||(a.addEvent(this.indicator,"mousedown",this),a.addEvent(e,"mouseup",this))),this.options.fade){this.wrapperStyle[a.style.transform]=this.scroller.translateZ;var o=a.style.transitionDuration;if(!o)return;this.wrapperStyle[o]=a.isBadAndroid?"0.0001ms":"0ms";var c=this;a.isBadAndroid&&s(function(){"0.0001ms"===c.wrapperStyle[o]&&(c.wrapperStyle[o]="0s")}),this.wrapperStyle.opacity="0"}}var s=e.requestAnimationFrame||e.webkitRequestAnimationFrame||e.mozRequestAnimationFrame||e.oRequestAnimationFrame||e.msRequestAnimationFrame||function(t){e.setTimeout(t,1e3/60)},a=function(){function i(e){return s===!1?!1:""===s?e:s+e.charAt(0).toUpperCase()+e.substr(1)}var r={},o=t.createElement("div").style,s=function(){for(var e,t=["t","webkitT","MozT","msT","OT"],n=0,i=t.length;i>n;n++)if(e=t[n]+"ransform",e in o)return t[n].substr(0,t[n].length-1);return!1}();r.getTime=Date.now||function(){return(new Date).getTime()},r.extend=function(e,t){for(var n in t)e[n]=t[n]},r.addEvent=function(e,t,n,i){e.addEventListener(t,n,!!i)},r.removeEvent=function(e,t,n,i){e.removeEventListener(t,n,!!i)},r.prefixPointerEvent=function(t){return e.MSPointerEvent?"MSPointer"+t.charAt(7).toUpperCase()+t.substr(8):t},r.momentum=function(e,t,i,r,o,s){var a,c,l=e-t,u=n.abs(l)/i;return s=void 0===s?6e-4:s,a=e+u*u/(2*s)*(0>l?-1:1),c=u/s,r>a?(a=o?r-o/2.5*(u/8):r,l=n.abs(a-e),c=l/u):a>0&&(a=o?o/2.5*(u/8):0,l=n.abs(e)+a,c=l/u),{destination:n.round(a),duration:c}};var a=i("transform");return r.extend(r,{hasTransform:a!==!1,hasPerspective:i("perspective")in o,hasTouch:"ontouchstart"in e,hasPointer:!(!e.PointerEvent&&!e.MSPointerEvent),hasTransition:i("transition")in o}),r.isBadAndroid=function(){var t=e.navigator.appVersion;if(/Android/.test(t)&&!/Chrome\/\d/.test(t)){var n=t.match(/Safari\/(\d+.\d)/);return n&&"object"==typeof n&&n.length>=2?parseFloat(n[1])<535.19:!0}return!1}(),r.extend(r.style={},{transform:a,transitionTimingFunction:i("transitionTimingFunction"),transitionDuration:i("transitionDuration"),transitionDelay:i("transitionDelay"),transformOrigin:i("transformOrigin"),touchAction:i("touchAction")}),r.hasClass=function(e,t){var n=new RegExp("(^|\\s)"+t+"(\\s|$)");return n.test(e.className)},r.addClass=function(e,t){if(!r.hasClass(e,t)){var n=e.className.split(" ");n.push(t),e.className=n.join(" ")}},r.removeClass=function(e,t){if(r.hasClass(e,t)){var n=new RegExp("(^|\\s)"+t+"(\\s|$)","g");e.className=e.className.replace(n," ")}},r.offset=function(e){for(var t=-e.offsetLeft,n=-e.offsetTop;e=e.offsetParent;)t-=e.offsetLeft,n-=e.offsetTop;return{left:t,top:n}},r.preventDefaultException=function(e,t){for(var n in t)if(t[n].test(e[n]))return!0;return!1},r.extend(r.eventType={},{touchstart:1,touchmove:1,touchend:1,mousedown:2,mousemove:2,mouseup:2,pointerdown:3,pointermove:3,pointerup:3,MSPointerDown:3,MSPointerMove:3,MSPointerUp:3}),r.extend(r.ease={},{quadratic:{style:"cubic-bezier(0.25, 0.46, 0.45, 0.94)",fn:function(e){return e*(2-e)}},circular:{style:"cubic-bezier(0.1, 0.57, 0.1, 1)",fn:function(e){return n.sqrt(1- --e*e)}},back:{style:"cubic-bezier(0.175, 0.885, 0.32, 1.275)",fn:function(e){var t=4;return(e-=1)*e*((t+1)*e+t)+1}},bounce:{style:"",fn:function(e){return(e/=1)<1/2.75?7.5625*e*e:2/2.75>e?7.5625*(e-=1.5/2.75)*e+.75:2.5/2.75>e?7.5625*(e-=2.25/2.75)*e+.9375:7.5625*(e-=2.625/2.75)*e+.984375}},elastic:{style:"",fn:function(e){var t=.22,i=.4;return 0===e?0:1==e?1:i*n.pow(2,-10*e)*n.sin(2*(e-t/4)*n.PI/t)+1}}}),r.tap=function(e,n){var i=t.createEvent("Event");i.initEvent(n,!0,!0),i.pageX=e.pageX,i.pageY=e.pageY,e.target.dispatchEvent(i)},r.click=function(n){var i,r=n.target;/(SELECT|INPUT|TEXTAREA)/i.test(r.tagName)||(i=t.createEvent(e.MouseEvent?"MouseEvents":"Event"),i.initEvent("click",!0,!0),i.view=n.view||e,i.detail=1,i.screenX=r.screenX||0,i.screenY=r.screenY||0,i.clientX=r.clientX||0,i.clientY=r.clientY||0,i.ctrlKey=!!n.ctrlKey,i.altKey=!!n.altKey,i.shiftKey=!!n.shiftKey,i.metaKey=!!n.metaKey,i.button=0,i.relatedTarget=null,i._constructed=!0,r.dispatchEvent(i))},r.getTouchAction=function(e,t){var n="none";return"vertical"===e?n="pan-y":"horizontal"===e&&(n="pan-x"),t&&"none"!=n&&(n+=" pinch-zoom"),n},r.getRect=function(e){if(e instanceof SVGElement){var t=e.getBoundingClientRect();return{top:t.top,left:t.left,width:t.width,height:t.height}}return{top:e.offsetTop,left:e.offsetLeft,width:e.offsetWidth,height:e.offsetHeight}},r}();i.prototype={version:"5.2.0-snapshot",_init:function(){this._initEvents(),(this.options.scrollbars||this.options.indicators)&&this._initIndicators(),this.options.mouseWheel&&this._initWheel(),this.options.snap&&this._initSnap(),this.options.keyBindings&&this._initKeys()},destroy:function(){this._initEvents(!0),clearTimeout(this.resizeTimeout),this.resizeTimeout=null,this._execEvent("destroy")},_transitionEnd:function(e){e.target==this.scroller&&this.isInTransition&&(this._transitionTime(),this.resetPosition(this.options.bounceTime)||(this.isInTransition=!1,this._execEvent("scrollEnd")))},_start:function(e){if(1!=a.eventType[e.type]){var t;if(t=e.which?e.button:e.button<2?0:4==e.button?1:2,0!==t)return}if(this.enabled&&(!this.initiated||a.eventType[e.type]===this.initiated)){!this.options.preventDefault||a.isBadAndroid||a.preventDefaultException(e.target,this.options.preventDefaultException)||e.preventDefault();var i,r=e.touches?e.touches[0]:e;this.initiated=a.eventType[e.type],this.moved=!1,this.distX=0,this.distY=0,this.directionX=0,this.directionY=0,this.directionLocked=0,this.startTime=a.getTime(),this.options.useTransition&&this.isInTransition?(this._transitionTime(),this.isInTransition=!1,i=this.getComputedPosition(),this._translate(n.round(i.x),n.round(i.y)),this._execEvent("scrollEnd")):!this.options.useTransition&&this.isAnimating&&(this.isAnimating=!1,this._execEvent("scrollEnd")),this.startX=this.x,this.startY=this.y,this.absStartX=this.x,this.absStartY=this.y,this.pointX=r.pageX,this.pointY=r.pageY,this._execEvent("beforeScrollStart")}},_move:function(e){if(this.enabled&&a.eventType[e.type]===this.initiated){this.options.preventDefault&&e.preventDefault();var t,i,r,o,s=e.touches?e.touches[0]:e,c=s.pageX-this.pointX,l=s.pageY-this.pointY,u=a.getTime();if(this.pointX=s.pageX,this.pointY=s.pageY,this.distX+=c,this.distY+=l,r=n.abs(this.distX),o=n.abs(this.distY),!(u-this.endTime>300&&10>r&&10>o)){if(this.directionLocked||this.options.freeScroll||(this.directionLocked=r>o+this.options.directionLockThreshold?"h":o>=r+this.options.directionLockThreshold?"v":"n"),"h"==this.directionLocked){if("vertical"==this.options.eventPassthrough)e.preventDefault();else if("horizontal"==this.options.eventPassthrough)return void(this.initiated=!1);l=0}else if("v"==this.directionLocked){if("horizontal"==this.options.eventPassthrough)e.preventDefault();else if("vertical"==this.options.eventPassthrough)return void(this.initiated=!1);c=0}c=this.hasHorizontalScroll?c:0,l=this.hasVerticalScroll?l:0,t=this.x+c,i=this.y+l,(t>0||t<this.maxScrollX)&&(t=this.options.bounce?this.x+c/3:t>0?0:this.maxScrollX),(i>0||i<this.maxScrollY)&&(i=this.options.bounce?this.y+l/3:i>0?0:this.maxScrollY),this.directionX=c>0?-1:0>c?1:0,this.directionY=l>0?-1:0>l?1:0,this.moved||this._execEvent("scrollStart"),this.moved=!0,this._translate(t,i),u-this.startTime>300&&(this.startTime=u,this.startX=this.x,this.startY=this.y)}}},_end:function(e){if(this.enabled&&a.eventType[e.type]===this.initiated){this.options.preventDefault&&!a.preventDefaultException(e.target,this.options.preventDefaultException)&&e.preventDefault();var t,i,r=(e.changedTouches?e.changedTouches[0]:e,a.getTime()-this.startTime),o=n.round(this.x),s=n.round(this.y),c=n.abs(o-this.startX),l=n.abs(s-this.startY),u=0,h="";if(this.isInTransition=0,this.initiated=0,this.endTime=a.getTime(),!this.resetPosition(this.options.bounceTime)){if(this.scrollTo(o,s),!this.moved)return this.options.tap&&a.tap(e,this.options.tap),this.options.click&&a.click(e),void this._execEvent("scrollCancel");if(this._events.flick&&200>r&&100>c&&100>l)return void this._execEvent("flick");if(this.options.momentum&&300>r&&(t=this.hasHorizontalScroll?a.momentum(this.x,this.startX,r,this.maxScrollX,this.options.bounce?this.wrapperWidth:0,this.options.deceleration):{destination:o,duration:0},i=this.hasVerticalScroll?a.momentum(this.y,this.startY,r,this.maxScrollY,this.options.bounce?this.wrapperHeight:0,this.options.deceleration):{destination:s,duration:0},o=t.destination,s=i.destination,u=n.max(t.duration,i.duration),this.isInTransition=1),this.options.snap){var d=this._nearestSnap(o,s);this.currentPage=d,u=this.options.snapSpeed||n.max(n.max(n.min(n.abs(o-d.x),1e3),n.min(n.abs(s-d.y),1e3)),300),o=d.x,s=d.y,this.directionX=0,this.directionY=0,h=this.options.bounceEasing}return o!=this.x||s!=this.y?((o>0||o<this.maxScrollX||s>0||s<this.maxScrollY)&&(h=a.ease.quadratic),void this.scrollTo(o,s,u,h)):void this._execEvent("scrollEnd")}}},_resize:function(){var e=this;clearTimeout(this.resizeTimeout),this.resizeTimeout=setTimeout(function(){e.refresh()},this.options.resizePolling)},resetPosition:function(e){var t=this.x,n=this.y;return e=e||0,!this.hasHorizontalScroll||this.x>0?t=0:this.x<this.maxScrollX&&(t=this.maxScrollX),!this.hasVerticalScroll||this.y>0?n=0:this.y<this.maxScrollY&&(n=this.maxScrollY),t==this.x&&n==this.y?!1:(this.scrollTo(t,n,e,this.options.bounceEasing),!0)},disable:function(){this.enabled=!1},enable:function(){this.enabled=!0},refresh:function(){a.getRect(this.wrapper),this.wrapperWidth=this.wrapper.clientWidth,this.wrapperHeight=this.wrapper.clientHeight;var e=a.getRect(this.scroller);this.scrollerWidth=e.width,this.scrollerHeight=e.height,this.maxScrollX=this.wrapperWidth-this.scrollerWidth,this.maxScrollY=this.wrapperHeight-this.scrollerHeight,this.hasHorizontalScroll=this.options.scrollX&&this.maxScrollX<0,this.hasVerticalScroll=this.options.scrollY&&this.maxScrollY<0,this.hasHorizontalScroll||(this.maxScrollX=0,this.scrollerWidth=this.wrapperWidth),this.hasVerticalScroll||(this.maxScrollY=0,this.scrollerHeight=this.wrapperHeight),this.endTime=0,this.directionX=0,this.directionY=0,a.hasPointer&&!this.options.disablePointer&&(this.wrapper.style[a.style.touchAction]=a.getTouchAction(this.options.eventPassthrough,!0),this.wrapper.style[a.style.touchAction]||(this.wrapper.style[a.style.touchAction]=a.getTouchAction(this.options.eventPassthrough,!1))),this.wrapperOffset=a.offset(this.wrapper),this._execEvent("refresh"),this.resetPosition()},on:function(e,t){this._events[e]||(this._events[e]=[]),this._events[e].push(t)},off:function(e,t){if(this._events[e]){var n=this._events[e].indexOf(t);n>-1&&this._events[e].splice(n,1)}},_execEvent:function(e){if(this._events[e]){var t=0,n=this._events[e].length;if(n)for(;n>t;t++)this._events[e][t].apply(this,[].slice.call(arguments,1))}},scrollBy:function(e,t,n,i){e=this.x+e,t=this.y+t,n=n||0,this.scrollTo(e,t,n,i)},scrollTo:function(e,t,n,i){i=i||a.ease.circular,this.isInTransition=this.options.useTransition&&n>0;var r=this.options.useTransition&&i.style;!n||r?(r&&(this._transitionTimingFunction(i.style),this._transitionTime(n)),this._translate(e,t)):this._animate(e,t,n,i.fn)},scrollToElement:function(e,t,i,r,o){if(e=e.nodeType?e:this.scroller.querySelector(e)){var s=a.offset(e);s.left-=this.wrapperOffset.left,s.top-=this.wrapperOffset.top;var c=a.getRect(e),l=a.getRect(this.wrapper);i===!0&&(i=n.round(c.width/2-l.width/2)),r===!0&&(r=n.round(c.height/2-l.height/2)),s.left-=i||0,s.top-=r||0,s.left=s.left>0?0:s.left<this.maxScrollX?this.maxScrollX:s.left,s.top=s.top>0?0:s.top<this.maxScrollY?this.maxScrollY:s.top,t=void 0===t||null===t||"auto"===t?n.max(n.abs(this.x-s.left),n.abs(this.y-s.top)):t,this.scrollTo(s.left,s.top,t,o)}},_transitionTime:function(e){if(this.options.useTransition){e=e||0;var t=a.style.transitionDuration;if(t){if(this.scrollerStyle[t]=e+"ms",!e&&a.isBadAndroid){this.scrollerStyle[t]="0.0001ms";var n=this;s(function(){"0.0001ms"===n.scrollerStyle[t]&&(n.scrollerStyle[t]="0s")})}if(this.indicators)for(var i=this.indicators.length;i--;)this.indicators[i].transitionTime(e)}}},_transitionTimingFunction:function(e){if(this.scrollerStyle[a.style.transitionTimingFunction]=e,this.indicators)for(var t=this.indicators.length;t--;)this.indicators[t].transitionTimingFunction(e)},_translate:function(e,t){if(this.options.useTransform?this.scrollerStyle[a.style.transform]="translate("+e+"px,"+t+"px)"+this.translateZ:(e=n.round(e),t=n.round(t),this.scrollerStyle.left=e+"px",this.scrollerStyle.top=t+"px"),this.x=e,this.y=t,this.indicators)for(var i=this.indicators.length;i--;)this.indicators[i].updatePosition()},_initEvents:function(t){var n=t?a.removeEvent:a.addEvent,i=this.options.bindToWrapper?this.wrapper:e;n(e,"orientationchange",this),n(e,"resize",this),this.options.click&&n(this.wrapper,"click",this,!0),this.options.disableMouse||(n(this.wrapper,"mousedown",this),n(i,"mousemove",this),n(i,"mousecancel",this),n(i,"mouseup",this)),a.hasPointer&&!this.options.disablePointer&&(n(this.wrapper,a.prefixPointerEvent("pointerdown"),this),n(i,a.prefixPointerEvent("pointermove"),this),n(i,a.prefixPointerEvent("pointercancel"),this),n(i,a.prefixPointerEvent("pointerup"),this)),a.hasTouch&&!this.options.disableTouch&&(n(this.wrapper,"touchstart",this),n(i,"touchmove",this),n(i,"touchcancel",this),n(i,"touchend",this)),n(this.scroller,"transitionend",this),n(this.scroller,"webkitTransitionEnd",this),n(this.scroller,"oTransitionEnd",this),n(this.scroller,"MSTransitionEnd",this)
}