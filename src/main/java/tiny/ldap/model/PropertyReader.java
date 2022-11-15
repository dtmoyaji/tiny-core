/*
 * Copyright 2021 bythe.
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
package tiny.ldap.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * プロパティファイルを読み込んで値を供給するクラス.
 *
 * @author daianjimax
 */
public class PropertyReader {

    private Properties properties = null;

    /**
     * コンストラクタ
     *
     * @param filePath プロパティファイルのパス
     */
    public PropertyReader(String filePath) {
        try {
            properties = new Properties();
            File f = new File(filePath);
            Logger.getLogger(this.getClass().getName()).info(f.getAbsolutePath());
            properties.load(
                    Files.newBufferedReader(Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * プロパティを取得する
     *
     * @param key 検索キー
     * @return プロパティ値
     */
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

}
