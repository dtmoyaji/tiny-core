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
 * マイグレーション機能定義
 *
 * @author daianjimax
 */
public interface IDatabaseMigrator {

    /**
     * テーブルを作成する。
     */
    public void create();

    /**
     * テーブルをドロップする。
     */
    public void drop();

    /**
     * テーブルを初期化する（開発用);
     */
    public void initiate();

}
