package mchorse.mclib.client.gui.utils.resizers;

import mchorse.mclib.client.gui.framework.elements.GuiElement;
import mchorse.mclib.client.gui.framework.elements.IGuiElement;
import mchorse.mclib.client.gui.utils.Area;

import java.util.ArrayList;
import java.util.List;

public abstract class AutomaticResizer extends DecoratedResizer
{
	public GuiElement parent;
	public int margin;
	public int padding;

	protected List<ChildResizer> resizers = new ArrayList<ChildResizer>();
	protected boolean collectChildren = true;

	public AutomaticResizer(GuiElement parent, int margin)
	{
		super(parent.flex());

		this.parent = parent;
		this.margin = margin;

		this.setup();
	}

	public AutomaticResizer dontCollect()
	{
		this.collectChildren = false;

		return this;
	}

	public AutomaticResizer padding(int padding)
	{
		this.padding = padding;

		return this;
	}

	public void reset()
	{
		this.resizers.clear();
	}

	public void setup()
	{
		for (IGuiElement child : this.parent.getChildren())
		{
			if (child instanceof GuiElement)
			{
				GuiElement element = (GuiElement) child;

				element.resizer(this.child(element));
			}
		}
	}

	public void add(GuiElement... elements)
	{
		for (GuiElement element : elements)
		{
			element.resizer(this.child(element));
		}
	}

	public IResizer child(GuiElement element)
	{
		ChildResizer child = new ChildResizer(this, element.resizer());

		if (this.collectChildren)
		{
			this.resizers.add(child);
		}

		return child;
	}

	@Override
	public void apply(Area area)
	{
		this.resizer.apply(area);
	}

	@Override
	public void add(GuiElement parent, GuiElement child)
	{
		child.resizer(this.child(child));
	}

	@Override
	public int getX()
	{
		return this.parent.area.x;
	}

	@Override
	public int getY()
	{
		return this.parent.area.y;
	}

	@Override
	public int getW()
	{
		return this.parent.area.w;
	}

	@Override
	public int getH()
	{
		return this.parent.area.h;
	}
}