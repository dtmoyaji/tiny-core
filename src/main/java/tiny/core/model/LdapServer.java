/*
 * Copyright 2021 Takahiro MURAKAMI.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tiny.core.model;

/**
 * 組込LDAP
 *
 * @author Takahiro MURAKAMI
 */
public class LdapServer {

    /**
     * 停止中
     */
    public static int STATUS_STOP = 0;

    /**
     * 実行中
     */
    public static int STATUS_RUNNING = 1;

    /**
     * 状態
     */
    private int status = STATUS_STOP;

    public void init() {

    }

    public void start() {

    }

    public void stop() {

    }

    public int status() {
        return this.status;
    }

}
