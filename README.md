# Mana Unification

A Minecraft Forge mod that provides a unified mana system for magic mods. Instead of having separate mana pools for each magic mod, Mana Unification creates a single, shared mana resource that all supported mods can use.

## Features

### Unified Mana System
- Single mana pool shared across all supported magic mods
- Custom mana attributes (Max Mana, Mana Regen) that can be modified by any mod
- Mana persists correctly across world saves and dimension changes

### Custom HUD
- Custom mana bar rendered above the health bar
- Configurable position offset
- Option to automatically move the bar up when wearing armor
- Toggle to show/hide numerical mana values

### Mod Support

#### Ars Nouveau
- Full integration with Ars Nouveau's spell system
- Spells use the unified mana pool
- Mana regeneration handled by unified system
- Ars Nouveau's default mana bar is hidden (uses unified bar)
- Glyph bonuses and book tier bonuses affect the unified mana pool

#### Iron's Spells 'n Spellbooks
- Full integration with Iron's spell system
- Spells use the unified mana pool
- Mana costs rebalanced to match Ars Nouveau scale
- Spellbook attributes affect the unified mana pool

#### Apotheosis
- Affix support for mana-related bonuses
- Gem support for mana attributes
- Attribute modifiers work with unified mana system

#### Electroblob's Wizardry Redux
- Wand mana consumption uses unified pool
- Spell casting integrated with unified system

#### Put Mana In
- Transfer unified mana to Ars Nouveau Source blocks
- Works seamlessly with the unified mana system

## Configuration

Configuration file: `config/manaunification-common.toml`

```toml
[mana]
    # Default maximum mana for players
    defaultMaxMana = 100.0
    # Default mana regeneration rate per second
    defaultManaRegen = 5.0

[hud]
    # X offset for the mana bar position
    manaBarOffsetX = 0
    # Y offset for the mana bar position
    manaBarOffsetY = 0
    # Move the mana bar up when player has armor equipped
    moveForArmor = true
    # Show numerical mana values on the HUD
    showManaValues = true
```

## For Developers

### Accessing the Mana System

Use the `ManaAPI` class for clean, easy access to the mana system:

```java
import com.nyfaria.manaunification.api.ManaAPI;
import com.nyfaria.manaunification.api.IManaHolder;

// Get current mana
double currentMana = ManaAPI.getCurrentMana(player);

// Get max mana
double maxMana = ManaAPI.getMaxMana(player);

// Get mana regen rate
double regenRate = ManaAPI.getManaRegen(player);

// Consume mana (returns true if successful)
boolean success = ManaAPI.consumeMana(player, 10.0);

// Add mana
ManaAPI.addMana(player, 5.0);

// Set mana directly
ManaAPI.setCurrentMana(player, 50.0);

// Get mana as percentage (0.0 to 1.0)
double percentage = ManaAPI.getManaPercentage(player);

// For more control, get the IManaHolder directly
Optional<IManaHolder> holder = ManaAPI.getManaHolder(entity);
holder.ifPresent(h -> {
    // Do something with the holder
});
```

### Mana Attributes

The mod registers two custom attributes that can be modified:
- `manaunification:max_mana` - Maximum mana pool size
- `manaunification:mana_regen` - Mana regeneration rate per second

These attributes can be modified through equipment, enchantments, or any other attribute modifier system.

## Installation

1. Install Minecraft Forge for your Minecraft version
2. Download the Mana Unification mod
3. Place the mod JAR file in your `mods` folder
4. (Optional) Install any supported magic mods

## Requirements

- Minecraft 1.20.1
- Forge 47.1.0+
- Capability Syncer library (included)

## Optional Dependencies

- Ars Nouveau
- Iron's Spells 'n Spellbooks
- Apotheosis
- Electroblob's Wizardry Redux
- Put Mana In

## License

All Rights Reserved.

## Credits

- **Nyfaria** - Lead Developer
- Thanks to all the magic mod developers for creating amazing content!
