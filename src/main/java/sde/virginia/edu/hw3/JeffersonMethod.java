/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.List;


/**
 * This class generates a congressional apportionment using an implementation of the <b>"Jefferson" method</b>. The
 * Jefferson method was in use from 1790 until 1830, when it was replaced.<br>
 * <br>
 * The Jefferson method works as follows:
 * <ol>
 *     <li>Calculate the initial divisor by taking the total population of all states divided by the intended
 *     number of apportioned representatives (that is, average population per representative</li>
 *     <li>Get each state's quota by dividing it's population by the divisor.</li>
 *     <li>Each state's quota is always rounded down, even if it's 4.9999999, for example</li>
 *     <li>This will result in an apportionment with too few representatives allocated in all practical cases</li>
 *     <li>Decrease the divisor and recalculate every state's quota (rounding down each time) until the target number
 *     of representatives is reached. (This class uses a binary search to achieve this effect)</li>
 *     <li>Return that apportionment as a {@link Representation} object. </li>
 * </ol>
 * <p>
 * Some implementation notes here:
 * <ul>
 *     <li>This approach tends to heavily favor larger states, who tend to be over represented relative to their
 *     population</li>
 *     <li>This implementation does not guarantee that every state has at least 1 representative. The reason
 *     for this is that the methodology I've been able to find for the Jefferson apportionment method doesn't
 *     explicitly specify what to do if the final divisor is less than a given state's population. This was, to
 *     the best of my research, never an issue encountered while the Jefferson method was in use, but it *would*
 *     be a problem using the 2020 census, as both Wyoming and Vermont have populations smaller than the final
 *     divisor that would be calculated. There is no single widely accepted solution to this problem.</li>
 *     <li>There are certain extremely rare situations where it is possible that the Jefferson method results in
 *     an unsolvable tie. In those instances, this class throws a {@link UnsolvableApportionmentException}.
 *     An example of where a tie could occur would be where there are only two states with equal population, and an
 *     odd number of representatives to Apportion.</li>
 * </ul>
 *
 * @see Representation
 * @author Will-McBurney
 */
public class JeffersonMethod implements ApportionmentMethod {

    /**
     * The maximum number of loops in the Binary search. Chosen as this is more than enough for a binary search
     * over the entire finite range of double to converge (experimentally 60 has been sufficient).
     */
    public static final int MAXIMUM_LOOP_LIMIT = 80;

    /**
     * Calculates the apportionment of representatives using the Jefferson algorithm
     *
     * @param states                A list of {@link State} objects
     * @param targetRepresentatives The intended number of representatives.
     * @return a {@link Representation} object describing how many representatives each state was apportioned.
     * @throws UnsolvableApportionmentException when the Jefferson method cannot provide a solution with the
     *                                          target number of representatives.
     */
    public Representation getRepresentation(List<State> states, int targetRepresentatives) {
        validateInputs(states, targetRepresentatives);

        var upperLimit = getDivisor(states, targetRepresentatives);
        var lowerLimit = 0.0;
        var numLoops = 0;
        var allocatedRepresentatives = 0;
        var representation = new Representation();

        while (numLoops < MAXIMUM_LOOP_LIMIT && allocatedRepresentatives != targetRepresentatives) {
            var mid = (upperLimit + lowerLimit) / 2;
            var roundedDownQuotas = getRoundedDownQuotas(states, mid);
            representation = new Representation(roundedDownQuotas);
            allocatedRepresentatives = representation.getAllocatedRepresentatives();

            if (allocatedRepresentatives < targetRepresentatives) {
                upperLimit = mid;
            }

            if (allocatedRepresentatives > targetRepresentatives) {
                lowerLimit = mid;
            }
            numLoops++;
        }

        if (allocatedRepresentatives != targetRepresentatives) {
            throw new UnsolvableApportionmentException(this, states, targetRepresentatives);
        }

        return representation;
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