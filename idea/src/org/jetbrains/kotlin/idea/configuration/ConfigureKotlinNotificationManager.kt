/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.configuration

import com.intellij.notification.Notification
import com.intellij.notification.NotificationsManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.configuration.ui.notifications.ConfigureKotlinNotification

object ConfigureKotlinNotificationManager: KotlinSingleNotificationManager<ConfigureKotlinNotification> {
    fun notify(project: Project, excludeModules: List<Module> = emptyList()) {
        notify(project, ConfigureKotlinNotification(project, excludeModules))
    }
}

interface KotlinSingleNotificationManager<in T: Notification> {
    fun notify(project: Project, notification: T) {
        val notificationsManager = NotificationsManager.getNotificationsManager() ?: return

        var isNotificationExists = false

        val notifications = notificationsManager.getNotificationsOfType(notification::class.java, project)
        for (oldNotification in notifications) {
            if (oldNotification == notification) {
                isNotificationExists = true
            }
            else {
                oldNotification.expire()
            }
        }
        if (!isNotificationExists) {
            notification.notify(project)
        }
    }
}

