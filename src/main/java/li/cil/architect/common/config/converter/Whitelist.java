package li.cil.architect.common.config.converter;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import java.util.List;
import java.util.Map;

public final class Whitelist extends AbstractFilterList<TileEntityFilter> {
    public boolean add(final Block block, final Map<IProperty<?>, Comparable<?>> properties, final int sortIndex, final Map<String, Object> nbtFilter, final Map<String, Object> nbtStripper) {
        final List<TileEntityFilter> filters = getFilters(block);

        final TileEntityFilter filter = new TileEntityFilter(new BlockStateFilter(block, properties), sortIndex, nbtFilter, nbtStripper);

        return addFilter(filter, filters, properties.isEmpty());
    }

    @Override
    protected BlockStateFilter getSelector(final TileEntityFilter filter) {
        return filter.getSelector();
    }

    @Override
    protected boolean addFilter(final TileEntityFilter filter, final List<TileEntityFilter> filters, final boolean isWildcard) {
        final boolean hasWildcard = !filters.isEmpty() && filters.get(filters.size() - 1).getSelector().getProperties().isEmpty();
        if (!super.addFilter(filter, filters, isWildcard)) {
            return false;
        }
        if (hasWildcard) {
            filters.add(filters.remove(filters.size() - 2));
        }
        return true;
    }
}
