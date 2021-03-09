/*
 * QNotified - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2021 dmca@ioctl.cc
 * https://github.com/ferredoxin/QNotified
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by ferredoxin.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/ferredoxin/QNotified/blob/master/LICENSE.md>.
 */
package ltd.nextalone.hook

import ltd.nextalone.util.clazz
import ltd.nextalone.util.replace
import ltd.nextalone.util.tryOrFalse
import me.singleneuron.qn_kernel.data.hostInfo
import me.singleneuron.qn_kernel.tlb.ConfigTable
import me.singleneuron.util.QQVersion
import nil.nadph.qnotified.base.annotation.FunctionEntry
import nil.nadph.qnotified.hook.CommonDelayableHook
import java.lang.reflect.Method

@FunctionEntry
object HideTotalNumber : CommonDelayableHook("na_hide_total_number") {

    override fun initOnce() = tryOrFalse {
        var className = "com.tencent.mobileqq.activity.aio.core.TroopChatPie"
        if (hostInfo.versionCode <= QQVersion.QQ_8_4_8) {
            className = "com.tencent.mobileqq.activity.aio.rebuild.TroopChatPie"
        }
        for (m: Method in className.clazz.methods) {
            val argt = m.parameterTypes
            if (m.name == ConfigTable.getConfig(HideTotalNumber::class.simpleName) && argt.isEmpty()) {
                m.replace(this, false)
            }
        }
    }
}
