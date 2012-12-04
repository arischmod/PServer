/* 
 * Copyright 2011 NCSR "Demokritos"
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
 * 
 */
package pserver.algorithms.metrics;

import java.sql.SQLException;
import pserver.domain.PServerVector;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author alexm
 * 
 * Pearson product-moment correlation coefficient
 */
public class PearsonCorrelationMetric implements VectorMetric {

    public float getDistance(PServerVector vec1, PServerVector vec2) throws SQLException {
        
        int commonFeatures = 0;
        float sumX = 0.0f;
        float sumY = 0.0f;
        float sumXY = 0.0f;
        float sumSqrX = 0.0f;
        float sumSqrY = 0.0f;

        Iterator<Map.Entry<String, Float>> it = vec1.getVectorValues().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Float> pair = it.next();
            Float otherVal = vec2.getVectorValues().get(pair.getKey());
            if (otherVal == null) {
                continue;
            }
            commonFeatures++;            
            float val = pair.getValue();
            sumX += val;
            sumY += otherVal;
            sumSqrX += val * val;
            sumSqrY += otherVal * otherVal;
            sumXY += val * otherVal;
        }

        if (commonFeatures == 0) {
            return 0.0f;
        } else {
            return (float) ((commonFeatures * sumXY - sumX * sumY)/ 
                    (
                    Math.sqrt(commonFeatures * sumSqrX- sumX * sumX) * 
                    Math.sqrt(commonFeatures * sumSqrY- sumY * sumY)));
        }
    }

    public float getMaximuxmCoefficientValue() {
        return 1.0f;
    }

    public float getMinimumCoefficientValue() {
        return 0.0f;
    }
}
