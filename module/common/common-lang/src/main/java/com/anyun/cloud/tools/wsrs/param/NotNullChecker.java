/*
 *
 *      NotNullChecker.java
 *      Copyright (C) <2015-?>  <twitchgg@yahoo.com>
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anyun.cloud.tools.wsrs.param;

import com.anyun.cloud.tools.StringUtils;
import com.anyun.cloud.tools.execption.ServiceParamCheckException;

/**
 *
 * @author TwitchGG
 * @date 2015-4-27
 * @version 1.0
 */
public class NotNullChecker implements ParamChecker {

    @Override
    public void check(String desc, Object value, String[] args) throws ServiceParamCheckException {
        if (value == null) {
            throw new ServiceParamCheckException(desc + " 不能为空");
        } else {
            if (value instanceof String) {
                if (StringUtils.isEmpty((String) value)) {
                    throw new ServiceParamCheckException(desc + " 不能为空");
                }
            } else if (value instanceof Integer || value instanceof Long) {
                if (Long.valueOf(value.toString()) == 0) {
                    throw new ServiceParamCheckException(desc + " 不能为空");
                }
            }
        }
    }
}
