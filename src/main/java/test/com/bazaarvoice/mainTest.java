package test.com.bazaarvoice;

import com.bazaarvoice.DocumentSearchImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class mainTest {
        DocumentSearchImpl documentSearch = new DocumentSearchImpl();



        @Test
        public void testHappyPath() {
            documentSearch.search("","");
//            assertEquals(   ,);
        }

}

