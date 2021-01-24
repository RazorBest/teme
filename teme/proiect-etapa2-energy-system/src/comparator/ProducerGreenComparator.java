package comparator;

import java.util.Arrays;
import java.util.Comparator;

import node.Producer;

public final class ProducerGreenComparator implements Comparator<Producer> {

    private static final String[] GREEN_ENERGIES = new String[] { "WIND", "SOLAR", "HYDRO" };

    @Override
    public int compare(final Producer o1, final Producer o2) {
        final boolean firstIsGreen = isGreen(o1);
        final boolean secondIsGreen = isGreen(o2);

        if (firstIsGreen && !secondIsGreen) {
            return -1;
        } else if (!firstIsGreen && secondIsGreen) {
            return 1;
        }

        return 0;
    }

    private boolean isGreen(final Producer producer) {
        final String energyType = producer.getEnergyType();

        return Arrays.stream(GREEN_ENERGIES).anyMatch(x -> x.equals(energyType));
    }

}
