package si.mkejzar.ignite.bugdemo;

import org.apache.ignite.IgniteSpringBean;

/**
 * @author matijak
 * @since 30/10/2017
 */
public class AutoActiveIgniteSpringBean extends IgniteSpringBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (!active()) {
            active(true);
        }

    }
}
