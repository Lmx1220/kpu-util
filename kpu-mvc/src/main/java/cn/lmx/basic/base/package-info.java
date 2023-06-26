
/**
 * @description: MVC基础包， 封装的 Controller、Service、Mapper等
 * <p>
 * 为什么Controller要拆分成这么多个？而Service和Mapper不拆分
 * 1，Controller 是给前端使用的，对于前端人员，看到的无用接口越少，越有利于对接
 * 2，过多的 Controller 接口暴露在外，增加被人恶意攻击风险
 * 3，Service和Mapper都是后端人员使用，丰富一些无所谓,就算会多个后端协同开发，因为都懂JAVA，沟通阅读起来没那么难
 * 4，Service和Mapper都继承了MP的IService和BaseMapper，没得选择！
 * @author lmx
 * @date 2023/7/4 14:27
 * @version 1.0
 */
package cn.lmx.basic.base;