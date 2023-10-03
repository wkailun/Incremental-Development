/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for AdamsMethod
 *
 * @author Will-McBurney
 */
class AdamsMethodTest {

    @Test
    void getRepresentation() {
        var de = new State("Delaware", 989948);
        var md = new State("Maryland", 6177224);
        var pa = new State("Pennsylvania", 13002700);
        var va = new State("Virginia", 8631393);
        var wv = new State("West Virginia", 1793716);

        var states = List.of(de, md, pa, va, wv);

        var adamsMethod = new AdamsMethod();
        var representation = adamsMethod.getRepresentation(states, 25);

        assertEquals(5, representation.getStates().size());
        assertEquals(1, representation.getRepresentativesFor(de));
        assertEquals(5, representation.getRepresentativesFor(md));
        assertEquals(10, representation.getRepresentativesFor(pa));
        assertEquals(7, representation.getRepresentativesFor(va));
        assertEquals(2, representation.getRepresentativesFor(wv));
    }
}

/*
 * Copyright (c) 2023. Paul "Will" McBurney <br>
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED &ldquo;AS IS&rdquo;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */