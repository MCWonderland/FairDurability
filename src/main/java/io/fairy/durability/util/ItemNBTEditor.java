package io.fairy.durability.util;

import kotlin.Lazy;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ItemNBTEditor {

    public static void set(ItemStack itemStack, Object value, String... keys) {
        try {
            final Field handle = Lazy.of(() -> {
                final Class<? extends ItemStack> itemStackClass = itemStack.getClass();
                try {
                    final Field field = itemStackClass.getDeclaredField("handle");
                    field.setAccessible(true);

                    return field;
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }).get();

            final Object o = handle.get(itemStack);
            final Field field = Lazy.of(() -> {
                final Class<?> aClass = o.getClass();
                final Field tag;
                try {
                    tag = aClass.getDeclaredField("tag");
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                tag.setAccessible(true);

                return tag;
            }).get();

            final Object nbt = field.get(o);
            NBTEditor.setTag(nbt, value, (Object[]) keys);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static class Lazy<T> {

        private static final Map<Class<?>, Object> MAP = new ConcurrentHashMap<>();

        public static <T> Lazy<T> of(Supplier<T> supplier) {
            return new Lazy<>(supplier);
        }

        private final Supplier<T> supplier;
        public Lazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        public T get() {
            return (T) MAP.computeIfAbsent(supplier.getClass(), c -> supplier.get());
        }

    }

}
