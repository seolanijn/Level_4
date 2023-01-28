"use strict";(self["webpackChunkcasestudyinfo3067"]=self["webpackChunkcasestudyinfo3067"]||[]).push([[883],{5572:(t,e,a)=>{a.d(e,{C:()=>r,_:()=>s});const l="/api/",s=async t=>{let e,a=o();try{let s=await fetch(`${l}${t}`,{method:"GET",headers:a});e=await s.json()}catch(s){e={error:`Error has occured: ${s.message}`}}return e},r=async(t,e)=>{let a,s=o();try{let r=await fetch(`${l}${t}`,{method:"POST",headers:s,body:JSON.stringify(e)});a=await r.json()}catch(r){a=r}return a},o=()=>{const t=new Headers,e=JSON.parse(sessionStorage.getItem("customer"));return e?(t.append("Content-Type","application/json"),t.append("Authorization","Bearer "+e.token)):t.append("Content-Type","application/json"),t}},7081:(t,e,a)=>{a.d(e,{p:()=>s,x:()=>l});const l=t=>t.toLocaleString("en-US",{style:"currency",currency:"USD"}),s=t=>{let e;e=void 0===t?new Date:new Date(Date.parse(t));let a=e.getDate(),l=e.getMonth()+1,s=e.getFullYear(),r=e.getHours(),o=e.getMinutes();return o<10&&(o="0"+o),s+"-"+l+"-"+a+" "+r+":"+o}},6883:(t,e,a)=>{a.r(e),a.d(e,{default:()=>Y});var l=a(9835),s=a(6970);const r={class:"text-center"},o={class:"q-mt-md"},d=["src"],c=(0,l._)("div",{class:"text-h5 q-mt-md"},"Order History",-1),u={class:"text-info text-h6 q-mt-lg q-mb-lg"},n=(0,l.Uk)("#"),i=(0,l.Uk)("Date"),m={class:"text-h3 text-center q-mt-xs text-secondary"},w={class:"text-subtitle1 text-center q-mt-sm text-secondary"},f=["src"],_=(0,l.Uk)(" Name "),g=(0,l.Uk)(" Quantities "),x=(0,l.Uk)(" Extended "),W=(0,l.Uk)(" S "),h=(0,l.Uk)(" O "),p=(0,l.Uk)(" B "),k=(0,l.Uk)(" Sub: "),y=(0,l.Uk)(" Tax(13%): "),q=(0,l.Uk)(" Total: ");function b(t,e,a,b,S,U){const v=(0,l.up)("q-avatar"),D=(0,l.up)("q-item-label"),C=(0,l.up)("q-item-section"),z=(0,l.up)("q-item"),Q=(0,l.up)("q-list"),Z=(0,l.up)("q-card"),$=(0,l.up)("q-btn"),A=(0,l.up)("q-card-actions"),O=(0,l.up)("q-card-section"),j=(0,l.up)("q-scroll-area"),I=(0,l.up)("q-dialog"),H=(0,l.Q2)("close-popup");return(0,l.wg)(),(0,l.iD)(l.HY,null,[(0,l._)("div",r,[(0,l._)("div",o,[(0,l.Wm)(v,{square:""},{default:(0,l.w5)((()=>[(0,l._)("img",{src:"icons/GameLogo.png"},null,8,d)])),_:1})]),c,(0,l._)("div",u,(0,s.zw)(b.state.status),1)]),(0,l.Wm)(Z,{class:"q-ma-sm"},{default:(0,l.w5)((()=>[(0,l.Wm)(Q,{highlight:""},{default:(0,l.w5)((()=>[(0,l.Wm)(z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(C,null,{default:(0,l.w5)((()=>[(0,l.Wm)(D,null,{default:(0,l.w5)((()=>[n])),_:1})])),_:1}),(0,l.Wm)(C,null,{default:(0,l.w5)((()=>[i])),_:1})])),_:1}),((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(b.state.orders,(t=>((0,l.wg)(),(0,l.j4)(z,{clickable:"",key:t.id,onClick:e=>b.selectorder(t.id)},{default:(0,l.w5)((()=>[(0,l.Wm)(C,null,{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(t.id),1)])),_:2},1024),(0,l.Wm)(C,null,{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.formatDate(t.orderDate)),1)])),_:2},1024)])),_:2},1032,["onClick"])))),128))])),_:1})])),_:1}),(0,l.Wm)(I,{modelValue:b.state.selectedAorder,"onUpdate:modelValue":e[0]||(e[0]=t=>b.state.selectedAorder=t),"transition-show":"rotate","transition-hide":"rotate"},{default:(0,l.w5)((()=>[(0,l.Wm)(Z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(A,{align:"right"},{default:(0,l.w5)((()=>[(0,l.wy)((0,l.Wm)($,{flat:"",label:"X",color:"primary",class:"text-h5"},null,512),[[H]])])),_:1}),(0,l.Wm)(O,null,{default:(0,l.w5)((()=>[(0,l._)("div",m," Order "+(0,s.zw)(b.state.orderDetails[0].orderId),1)])),_:1}),(0,l.Wm)(O,null,{default:(0,l.w5)((()=>[(0,l._)("div",w,(0,s.zw)(b.formatDate(b.state.orderSelected.orderDate)),1)])),_:1}),(0,l.Wm)(O,{class:"q-pa-none text-center"},{default:(0,l.w5)((()=>[(0,l.Wm)(v,{square:""},{default:(0,l.w5)((()=>[(0,l._)("img",{src:"/cart.png"},null,8,f)])),_:1})])),_:1}),(0,l.Wm)(j,{style:{height:"45vh",width:"90vw"}},{default:(0,l.w5)((()=>[(0,l.Wm)(Z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(Q,{separator:""},{default:(0,l.w5)((()=>[(0,l.Wm)(z,{class:"text-bold bg-white text-secondary",style:{height:"1vh"}},{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4"},{default:(0,l.w5)((()=>[_])),_:1}),(0,l.Wm)(C,{class:"col-4"},{default:(0,l.w5)((()=>[g])),_:1}),(0,l.Wm)(C,{class:"text-right"},{default:(0,l.w5)((()=>[x])),_:1})])),_:1}),(0,l.Wm)(z,{class:"text-bold bg-white text-secondary",style:{height:"1vh"}},{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4"}),(0,l.Wm)(C,{class:"col-1"},{default:(0,l.w5)((()=>[W])),_:1}),(0,l.Wm)(C,{class:"col-1"},{default:(0,l.w5)((()=>[h])),_:1}),(0,l.Wm)(C,{class:"col-1"},{default:(0,l.w5)((()=>[p])),_:1}),(0,l.Wm)(C,{class:""})])),_:1}),((0,l.wg)(!0),(0,l.iD)(l.HY,null,(0,l.Ko)(b.state.orderDetails,(t=>((0,l.wg)(),(0,l.j4)(z,{key:t.orderid,clickable:""},{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4 text-left text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(t.productName),1)])),_:2},1024),(0,l.Wm)(C,{class:"col-1 text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(t.qtySold),1)])),_:2},1024),(0,l.Wm)(C,{class:"col-1 text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(t.qtyOrdered),1)])),_:2},1024),(0,l.Wm)(C,{class:"col-1 text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(t.qtyBackOrdered),1)])),_:2},1024),(0,l.Wm)(C,{class:"text-right text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.formatCurrency(t.msrp*t.qtySold)),1)])),_:2},1024)])),_:2},1024)))),128)),(0,l.Wm)(z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4 text-left"}),(0,l.Wm)(C,{class:"col-2"}),(0,l.Wm)(C,{class:"text-bold text-right"},{default:(0,l.w5)((()=>[k])),_:1}),(0,l.Wm)(C,{class:"col-4 text-right text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.formatCurrency(b.state.sub)),1)])),_:1})])),_:1}),(0,l.Wm)(z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4 text-left"}),(0,l.Wm)(C,{class:"col-1"}),(0,l.Wm)(C,{class:"text-bold text-right"},{default:(0,l.w5)((()=>[y])),_:1}),(0,l.Wm)(C,{class:"col-4 text-right text-dark"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.formatCurrency(b.state.tax)),1)])),_:1})])),_:1}),(0,l.Wm)(z,null,{default:(0,l.w5)((()=>[(0,l.Wm)(C,{class:"col-4 text-left"}),(0,l.Wm)(C,{class:"col-2"}),(0,l.Wm)(C,{class:"text-bold col-1 text-right text-secondary"},{default:(0,l.w5)((()=>[q])),_:1}),(0,l.Wm)(C,{class:"col-4 text-right text-info"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.formatCurrency(b.state.total)),1)])),_:1})])),_:1})])),_:1})])),_:1})])),_:1}),(0,l.Wm)(O,{class:"text-subtitle1 text-center text-info text-h6 q-mb-md q-mt-lg q-pa-none"},{default:(0,l.w5)((()=>[(0,l.Uk)((0,s.zw)(b.state.dialogStatus),1)])),_:1})])),_:1})])),_:1},8,["modelValue"])],64)}var S=a(499),U=a(5572),v=a(7081);const D={setup(){let t=(0,S.qj)({status:"",dialogStatus:"",selectedAorder:!1,orderDetails:[],orderSelected:{},sub:0,tax:0,total:0});(0,l.bv)((()=>{e()}));const e=async()=>{try{let e=JSON.parse(sessionStorage.getItem("customer"));const a=await(0,U._)(`order/${e.email}`);void 0===a.error?(t.orders=a,t.status=`loaded ${t.orders.length} orders`):t.status=a.error}catch(e){console.log(e),t.status=`Error has occured: ${e.message}`}},a=async e=>{try{let a=JSON.parse(sessionStorage.getItem("customer"));const l=await(0,U._)(`order/${e}/${a.email}`);t.orderDetails=l,t.dialogStatus=`details for order ${e}`,t.selectedAorder=!0,t.orderSelected=t.orders.find((t=>t.id===e)),t.sub=t.orderSelected.orderAmount,t.tax=.13*t.orderSelected.orderAmount,t.total=t.sub+t.tax}catch(a){console.log(a),t.status=`Error has occured: ${a.message}`}};return{state:t,formatDate:v.p,selectorder:a,formatCurrency:v.x}}};var C=a(1639),z=a(1357),Q=a(4458),Z=a(3246),$=a(490),A=a(1233),O=a(3115),j=a(7743),I=a(1821),H=a(4455),N=a(3190),T=a(1019),E=a(2146),B=a(9984),J=a.n(B);const L=(0,C.Z)(D,[["render",b]]),Y=L;J()(D,"components",{QAvatar:z.Z,QCard:Q.Z,QList:Z.Z,QItem:$.Z,QItemSection:A.Z,QItemLabel:O.Z,QDialog:j.Z,QCardActions:I.Z,QBtn:H.Z,QCardSection:N.Z,QScrollArea:T.Z}),J()(D,"directives",{ClosePopup:E.Z})}}]);